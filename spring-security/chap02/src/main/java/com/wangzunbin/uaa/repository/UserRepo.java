package com.wangzunbin.uaa.repository;

import com.wangzunbin.uaa.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ClassName:UserRepo
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 10:09
 */
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findOptionalByUsername(String username);

    long countByUsername(String username);

    long countByEmail(String email);

    long countByEmailAndUsernameIsNot(String email, String username);

    long countByMobile(String mobile);

    long countByMobileAndUsernameIsNot(String mobile, String username);
}