package com.wangzunbin.uaa.service;

import com.wangzunbin.uaa.domain.Auth;
import com.wangzunbin.uaa.repository.UserRepo;
import com.wangzunbin.uaa.util.JWTUtil;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:UserService
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 0:13
 */

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public Auth login(String username, String password) throws AuthenticationException{
        return userRepo.findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> new Auth(
                        jwtUtil.createAccessToken(user),
                        jwtUtil.createRefreshToken(user)
                ))
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
    }
}
