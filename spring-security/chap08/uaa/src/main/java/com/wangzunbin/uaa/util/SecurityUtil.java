package com.wangzunbin.uaa.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Optional;

import lombok.val;

/**
 * ClassName:SecurityUtil
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 22:16
 */
public class SecurityUtil {

    private static final String ANONYMOUS_USER = "anonymous";

    public static String getCurrentLogin() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(principal -> {
                    // 大多数 AuthenticationManager 会返回 UserDetails ，提供更多信息
                    if (principal instanceof UserDetails) {
                        val userDetails = (UserDetails) principal;
                        return userDetails.getUsername();
                    }
                    // 如果没有更多信息，可以看一下是否是一个 Principal
                    if (principal instanceof Principal) {
                        return ((Principal) principal).getName();
                    }
                    // 其他情况看作是一个用户名
                    return String.valueOf(principal);
                })
                // 如果未认证，那么 Authentication 为 Null
                // 可以在未受安全保护的 URL 中实验
                // 此次返回匿名用户
                .orElse(ANONYMOUS_USER);
    }
}