package com.wangzunbin.uaa.service;

import com.wangzunbin.uaa.domain.Auth;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.repository.RoleRepo;
import com.wangzunbin.uaa.repository.UserRepo;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.util.JwtUtil;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
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
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepo roleRepo;

    public Auth login(String username, String password) throws AuthenticationException{
        return userRepo.findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> new Auth(
                        jwtUtil.createAccessToken(user),
                        jwtUtil.createRefreshToken(user)
                ))
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
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
        return roleRepo.findOptionalByAuthority(Constants.ROLE_USER).map(role -> {
            val userToSave = user.withAuthorities(Set.of(role))
                    .withPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(userToSave);
        }).orElseThrow();
    }
}
