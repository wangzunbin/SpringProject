package com.wangzunbin.uaa.rest;

import com.wangzunbin.uaa.domain.Auth;
import com.wangzunbin.uaa.domain.MfaType;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.domain.dto.LoginDto;
import com.wangzunbin.uaa.domain.dto.SendTotpDto;
import com.wangzunbin.uaa.domain.dto.TotpVerificationDto;
import com.wangzunbin.uaa.domain.dto.UserDto;
import com.wangzunbin.uaa.exception.DuplicateProblem;
import com.wangzunbin.uaa.exception.InvalidTotpProblem;
import com.wangzunbin.uaa.exception.UserAccountExpiredProblem;
import com.wangzunbin.uaa.exception.UserAccountLockedProblem;
import com.wangzunbin.uaa.exception.UserNotEnabledProblem;
import com.wangzunbin.uaa.service.IEmailService;
import com.wangzunbin.uaa.service.ISmsService;
import com.wangzunbin.uaa.service.UserCacheService;
import com.wangzunbin.uaa.service.UserService;
import com.wangzunbin.uaa.util.JwtUtil;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * ClassName:AuthorizeResource  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:30   <br/>
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/authorize")
public class AuthorizeResource {

    private final UserService userService;
    private final static String PREFIX = "Bearer";
    private final JwtUtil jwtUtil;
    private final UserCacheService userCacheService;
    private final ISmsService smsService;
    private final IEmailService emailService;

    @PostMapping(value="/register")
    public void register(@RequestBody @Valid UserDto userDto) {
        // TODO: 1. 检查username, email, mobile都是唯一的, 所以要查询数据库确保唯一
        // TODO: 2. 我们需要userDto转换成User, 我们给一个默认角色{ROLE_USER}然后保存
        if(userService.isUsernameExisted(userDto.getUsername())) {
            throw new DuplicateProblem("用户名重复", "用户名重复详细信息");
        }
        if(userService.isEmailExisted(userDto.getEmail())) {
            throw new DuplicateProblem("邮箱重复", "邮箱重复详细信息");
        }
        if(userService.isMobileExisted(userDto.getMobile())) {
            throw new DuplicateProblem("手机号码重复", "手机号码重复详细信息");
        }
        val user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .mobile(userDto.getMobile())
                .password(userDto.getPassword())
                .build();
        // TODO: 3. 我们给个默认角色{ROLE_USER}, 然后保存
        userService.register(user);
    }

    @PostMapping("/token/refresh")
    public Auth refreshToken(@RequestHeader(name = "Authorization")String authorization, @RequestParam String refreshToken) throws AccessDeniedException{
        val accessToken = authorization.replace(PREFIX, "");
        if(jwtUtil.validateRefreshToken(refreshToken) && jwtUtil.validateAccessTokenWithoutExpiration(accessToken)) {
            return new Auth(jwtUtil.createAccessTokenWithRefreshToken(refreshToken), refreshToken);
        }
        throw new AccessDeniedException("访问被拒绝");
    }


    /**
     * 登录的时候发现需要第二次认证的时候, 前端拿到返回码, 进到下个验证页面, 输入验证, 调用/authorize/totp接口发送验证码
     */
    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) throws Exception{
       return userService.findOptionalByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())
                .map(user -> {
                    // 1.升级密码;
                    userService.updatePassword(user, loginDto.getPassword());
                    // 2.验证
                    if (!user.isEnabled()) {
                        throw new UserNotEnabledProblem();
                    }
                    if (!user.isAccountNonLocked()) {
                        throw new UserAccountLockedProblem();
                    }
                    if (!user.isAccountNonExpired()) {
                        throw new UserAccountExpiredProblem();
                    }
                    // 3.判断usingMfa, 如果是false, 我们就直接返回token
                    if(!user.isUsingMfa()) {
                        return ResponseEntity.ok().body(userService.login(loginDto.getUsername(), loginDto.getPassword()));
                    }
                    // 4.使用多因子认证
                    val mfaId = userCacheService.cacheUser(user);
                    // 5. "X-Authenticate": "mfa", "realm=" + mfaId
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .header("X-Authenticate", "mfa", "realm=" + mfaId)
                            .build();
                })
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
    }

    @PutMapping("/totp")
    public void sendTotp(@Valid @RequestBody SendTotpDto sendTotpDto) {
        userCacheService.retrieveUser(sendTotpDto.getMfaId())
                // 返回一对
                .flatMap(user -> userService.createTotp(user).map(code -> Pair.of(user, code)))
                // 有值执行下面的代码
                .ifPresentOrElse(pair -> {
                    log.debug("totp: {}", pair.getSecond());
                    if (sendTotpDto.getMfaType() == MfaType.SMS) {
                        smsService.send(pair.getFirst().getMobile(), pair.getSecond());
                    } else {
                        emailService.send(pair.getFirst().getEmail(), pair.getSecond());
                    }
                }, () -> {
                    throw new InvalidTotpProblem();
                });
    }


    @PostMapping("/totp")
    public Auth verifyTotp(@Valid @RequestBody TotpVerificationDto totpVerificationDto) {
        return userCacheService.verifyTotp(totpVerificationDto.getMfaId(), totpVerificationDto.getCode())
                .map(user -> userService.login(user.getUsername(), user.getPassword()))
                .orElseThrow(InvalidTotpProblem::new);
    }
}
