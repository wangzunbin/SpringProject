package com.wangzunbin.uaa.security.jwt;

import com.wangzunbin.uaa.config.AppProperties;
import com.wangzunbin.uaa.util.CollectionUtil;
import com.wangzunbin.uaa.util.JWTUtil;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static java.util.stream.Collectors.toList;

/**
 * ClassName:JwtFilter
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 22:35
 */

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final AppProperties appProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkJwtToken(request)) {
            //有值的情况
            validateToken(request).filter(claims -> Objects.nonNull(claims.get("authorities")))
                    .ifPresentOrElse(this::setupSpringAuthentication, SecurityContextHolder::clearContext);
        }
        filterChain.doFilter(request, response);
    }

    private void setupSpringAuthentication(Claims claims) {
        val rawList = CollectionUtil.convertObjectToList(claims.get("authorities"));
        val authorities = rawList.stream().map(String::valueOf).map(SimpleGrantedAuthority::new).collect(toList());
        val authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Optional<Claims> validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(appProperties.getJwt().getHeader()).replace(appProperties.getJwt().getPrefix(), "");
        try {
            return Optional.of(Jwts.parserBuilder().setSigningKey(JWTUtil.key).build().parseClaimsJws(jwtToken).getBody());
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 检查JWT TOKEN是否在HTTP报头中
     *
     * @param request http请求
     * @return 是否是合法JWT Token
     */
    private boolean checkJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(appProperties.getJwt().getHeader());
        return StringUtils.hasLength(authorizationHeader) && authorizationHeader.startsWith(appProperties.getJwt().getPrefix());
    }
}
