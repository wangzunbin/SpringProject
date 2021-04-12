package com.wangzunbin.uaa.service;

import com.wangzunbin.uaa.domain.Auth;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.repository.RoleRepo;
import com.wangzunbin.uaa.repository.UserRepo;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.util.JwtUtil;
import com.wangzunbin.uaa.util.TotpUtil;

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

    public Auth login(String username, String password){
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

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public Auth login(UserDetails userDetails) {
        return new Auth(jwtUtil.createAccessToken(userDetails), jwtUtil.createRefreshToken(userDetails));
    }

    public boolean isValidUser(Authentication authentication, String username){
        return authentication.getName().equals(username);
    }
}
