package com.wangzunbin.uaa.service;

import com.wangzunbin.uaa.annotation.RoleAdminOrSelfWithUserParam;
import com.wangzunbin.uaa.domain.Auth;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.repository.RoleRepo;
import com.wangzunbin.uaa.repository.UserRepo;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.util.JwtUtil;
import com.wangzunbin.uaa.util.TotpUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * ClassName:UserService
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 0:13
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepo roleRepo;
    private final TotpUtil totpUtil;

    /**
     * 根据 UserDetails 生成 accessToken 和 refreshToken
     *
     * @param userDetails 用户信息
     * @return token 对
     */
    public Auth login(UserDetails userDetails) {
        return new Auth(jwtUtil.createAccessToken(userDetails), jwtUtil.createRefreshToken(userDetails));
    }

    public boolean isUsernameExisted(String username){
        return userRepo.countByUsername(username) > 0;
    }

    public boolean isEmailExisted(String email){
        return userRepo.countByEmail(email) > 0;
    }

    public boolean isMobileExisted(String mobile){
        return userRepo.countByMobile(mobile) > 0;
    }

    @Transactional
    public User register(User user){
        return roleRepo.findOptionalByRoleName(Constants.ROLE_USER).map(role -> {
            val userToSave = user.withRoles(Set.of(role))
                    .withPassword(passwordEncoder.encode(user.getPassword()))
                    .withMfaKey(totpUtil.encodeKeyToString());
            return userRepo.save(userToSave);
        }).orElseThrow();
    }

    public Optional<User> findOptionalByUsernameAndPassword(String username, String password) {
        return findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    public Optional<User> findOptionalByUsername(String username) {
        return userRepo.findOptionalByUsername(username);
    }

    public Optional<User> findOptionalByEmail(String email) {
        return userRepo.findOptionalByEmail(email);
    }

    public void updatePassword(User user, String rawPassword) {
        if (passwordEncoder.upgradeEncoding(user.getPassword())) {
            userRepo.save(user.withPassword(passwordEncoder.encode(rawPassword)));
        }
    }

    public Optional<String> createTotp(User user) {
        return totpUtil.createTotp(user.getMfaKey());
    }

    public Auth loginWithTotp(User user) {
        val toSave = user.withMfaKey(totpUtil.encodeKeyToString());
        val saved = saveUser(toSave);
        return login(saved);
    }

    /**
     * 保存用户
     * hasRole hasAuthority
     * @param user 被保存的对象
     * @return 返回被保存的对象
     * 下面的==可以用equals来代替
     */
    @Transactional
    @RoleAdminOrSelfWithUserParam
    public User saveUser(User user) {
        return userRepo.save(user);
    }


    public boolean isValidUser(Authentication authentication, String username){
        return authentication.getName().equals(username);
    }
}
