package com.wangzunbin.uaa.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.val;

import static java.util.stream.Collectors.toList;

/**
 * ClassName:JWTUtil
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 21:25
 */
@Component
public class JWTUtil {

    // 用于签名
    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String createJWToken(UserDetails userDetails) {
        val now = System.currentTimeMillis();
        return Jwts.builder()
                .setId("wzb")
                .claim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                // 过期时间
                .setExpiration(new Date(now + 60_000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
