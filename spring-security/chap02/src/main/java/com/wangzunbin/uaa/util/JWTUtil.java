package com.wangzunbin.uaa.util;

import com.wangzunbin.uaa.config.AppProperties;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static java.util.stream.Collectors.toList;

/**
 * ClassName:JWTUtil
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 21:25
 */

@RequiredArgsConstructor
@Component
public class JWTUtil {

    // 用于签名的访问令牌的秘钥
    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    // 用于签名的刷新令牌的秘钥
    public static final Key refreshKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final AppProperties appProperties;

    public String createAccessToken(UserDetails userDetails){
        return createJWToken(userDetails, appProperties.getJwt().getAccessTokenExpireTime(), key);
    }

    public String createRefreshToken(UserDetails userDetails){
        return createJWToken(userDetails, appProperties.getJwt().getRefreshTokenExpireTime(), refreshKey);
    }

    public String createJWToken(UserDetails userDetails, long timeToExpire, Key key) {
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
