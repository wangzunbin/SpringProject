package com.wangzunbin.uaa.util;

import com.wangzunbin.uaa.config.AppProperties;
import com.wangzunbin.uaa.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Base64;
import java.util.Map;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * ClassName:PasswordEncoderTest
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/11 20:00
 */

@ExtendWith(SpringExtension.class)
@Slf4j
public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        val idForEncode = "bcrypt";
        // 要支持的多种编码器
        val encoders = Map.of(
                idForEncode, new BCryptPasswordEncoder(),
                "SHA-1", new MessageDigestPasswordEncoder("SHA-1")
        );
        passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Test
    void test1() {

        val password1 = "{bcrypt}$2a$10$NG3XLqIxB7EPUQZRrj8To.xKsrwcvZQuz.lc1k9sY7X2/rQ4Xnby2";
        val password2 = "{bcrypt}$2a$10$NG3XLqIxB7EPUQZRrj8To.xKsrwcvZQuz.lc1k9sY7X2/rQ4Xnby2";
        boolean matches = passwordEncoder.matches(password1, password2);
        log.debug("matches: {}", matches);
    }

    @Test
    public void givenPasswordEncodedInOldFormat_thenUpgradeEncodingSuccess() {
        val oldPasswordHash = "{SHA-1}7ce0359f12857f2a90c7de465f40a95f01cb5da9";
        val rawPassword = "abcd1234";
        assertTrue(passwordEncoder.matches(rawPassword, oldPasswordHash));
        assertTrue(passwordEncoder.upgradeEncoding(oldPasswordHash));
        val newPasswordHash = passwordEncoder.encode(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, newPasswordHash));
    }

    @Test
    public void givenPasswordEncodedInOldFormat_thenUpgradeEncodingSuccess2() {
        val oldPasswordHash = "{bcrypt}$2a$10$NG3XLqIxB7EPUQZRrj8To.xKsrwcvZQuz.lc1k9sY7X2/rQ4Xnby2";
        val rawPassword = "123456";
        assertTrue(passwordEncoder.matches(rawPassword, oldPasswordHash));
//        assertTrue(passwordEncoder.upgradeEncoding(oldPasswordHash));
        val newPasswordHash = passwordEncoder.encode(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, newPasswordHash));
    }
}
