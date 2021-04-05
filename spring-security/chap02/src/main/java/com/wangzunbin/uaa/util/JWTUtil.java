package com.wangzunbin.uaa.util;

import com.wangzunbin.uaa.config.AppProperties;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

    public String createAccessToken(UserDetails userDetails) {
        return createJWToken(userDetails, appProperties.getJwt().getAccessTokenExpireTime(), key);
    }

    public String createRefreshToken(UserDetails userDetails) {
        return createJWToken(userDetails, appProperties.getJwt().getRefreshTokenExpireTime(), refreshKey);
    }

    public boolean validateAccessTokenWithoutExpiration(String token) {
        return validateToken(token, key, false);
    }

    public boolean validateToken(String token) {
        return validateToken(token, key, true);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshKey, true);
    }

    public String createAccessTokenWithRefreshToken(String token) {
        return parseClaims(token, refreshKey)
                .map(claims -> Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + appProperties.getJwt().getAccessTokenExpireTime()))
                        .setIssuedAt(new Date())
                        .signWith(key, SignatureAlgorithm.HS512).compact())
                .orElseThrow(() -> new AccessDeniedException("访问被拒绝"));
    }

    public Optional<Claims> parseClaims(String token, Key key) {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public boolean validateToken(String token, Key key, boolean isExpiredInvalid) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            if (e instanceof ExpiredJwtException) {
                return !isExpiredInvalid;
            } else {
                return false;
            }
        }
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
