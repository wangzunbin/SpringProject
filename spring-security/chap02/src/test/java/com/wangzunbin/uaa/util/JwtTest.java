package com.wangzunbin.uaa.util;

import com.wangzunbin.uaa.config.AppProperties;
import com.wangzunbin.uaa.domain.Role;
import com.wangzunbin.uaa.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * ClassName:JetTest
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 21:36
 */

// junit5
@ExtendWith(SpringExtension.class)
@Slf4j
public class JwtTest {


    private JWTUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JWTUtil(new AppProperties());
    }

    @Test
    public void givenUserDetails_thenCreateTokenSuccess() {
        val username = "user";
        val authorities = Set.of(
                Role.builder().authority("ROLE_USER").build(),
                Role.builder().authority("ROLE_ADMIN").build());
        val user = User.builder()
                .username(username)
                .authorities(authorities)
                .build();
        // 创建 jwt
        val token = jwtUtil.createAccessToken(user);
        log.debug("获取到的Token: {}", token);
        // 解析
        val parsedClaims = Jwts.parserBuilder().setSigningKey(JWTUtil.key)
                .build().parseClaimsJws(token).getBody();
        assertEquals(username, parsedClaims.getSubject(), "解析后 Subject 应是用户名");
    }
}
