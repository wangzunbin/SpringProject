package com.wangzunbin.uaa.service;

import com.wangzunbin.uaa.annotation.RoleAdminOrSelfWithUserParam;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.repository.RoleRepo;
import com.wangzunbin.uaa.repository.UserRepo;
import com.wangzunbin.uaa.util.TotpUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static com.wangzunbin.uaa.util.Constants.ROLE_USER;

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
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TotpUtil totpUtil;

    /**
     * 注册一个新用户
     *
     * @param user 用户实体
     * @return 保存后的对象
     */
    @org.springframework.transaction.annotation.Transactional
    public User register(User user) {
        return roleRepo.findOptionalByRoleName(ROLE_USER)
                .map(role -> {
                    val userToSave = user
                            .withRoles(Set.of(role))
                            .withPassword(passwordEncoder.encode(user.getPassword()))
                            .withMfaKey(totpUtil.encodeKeyToString());
                    return userRepo.save(userToSave);
                })
                .orElseThrow();
    }

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    public Optional<User> findOptionalByUsername(String username) {
        return userRepo.findOptionalByUsername(username);
    }

    /**
     * 根据电子邮件查找用户
     *
     * @param email 电子邮件
     * @return 用户
     */
    public Optional<User> findOptionalByEmail(String email) {
        return userRepo.findOptionalByEmail(email);
    }

    /**
     * 根据用户名和密码进行匹配检查
     *
     * @param username 用户名
     * @param password 明文密码
     * @return 用户
     */
    public Optional<User> findOptionalByUsernameAndPassword(String username, String password) {
        return findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 保存后的用户
     */
    @org.springframework.transaction.annotation.Transactional
    @RoleAdminOrSelfWithUserParam
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    /**
     * 创建 Totp
     *
     * @param user 用户
     * @return Totp
     */
    public Optional<String> createTotp(User user) {
        return totpUtil.createTotp(user.getMfaKey());
    }

    /**
     * 如果用户密码采用的是旧的编码算法，那么利用此方法可以升级编码
     *
     * @param user        用户
     * @param rawPassword 明文密码
     */
    @Transactional
    public void upgradePasswordEncodingIfNeeded(User user, String rawPassword) {
        if (passwordEncoder.upgradeEncoding(user.getPassword())) {
            userRepo.save(user.withPassword(passwordEncoder.encode(rawPassword)));
        }
    }
}
