package com.wangzunbin.uaa.rest;

import com.wangzunbin.uaa.domain.Auth;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.domain.dto.LoginDto;
import com.wangzunbin.uaa.domain.dto.UserDto;
import com.wangzunbin.uaa.exception.DuplicateProblem;
import com.wangzunbin.uaa.service.UserService;
import com.wangzunbin.uaa.util.JwtUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * ClassName:AuthorizeResource  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:30   <br/>
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/authorize")
public class AuthorizeResource {

    private final UserService userService;
    private final static String PREFIX = "Bearer";
    private final JwtUtil jwtUtil;

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

    @PostMapping("/token")
    public Auth login(@RequestBody @Valid LoginDto loginDto) throws Exception{
        return userService.login(loginDto.getUsername(), loginDto.getPassword());
    }

    @PostMapping("/token/refresh")
    public Auth refreshToken(@RequestHeader(name = "Authorization")String authorization, @RequestParam String refreshToken) throws AccessDeniedException{
        val accessToken = authorization.replace(PREFIX, "");
        if(jwtUtil.validateRefreshToken(refreshToken) && jwtUtil.validateAccessTokenWithoutExpiration(accessToken)) {
            return new Auth(jwtUtil.createAccessTokenWithRefreshToken(refreshToken), refreshToken);
        }
        throw new AccessDeniedException("访问被拒绝");
    }
}
