package com.wangzunbin.uaa.config;

import com.wangzunbin.uaa.security.auth.ldap.LDAPAuthenticationProvider;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:LoginSecurityConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/3/28 17:53
 */
@Configuration
@Order(100)
@RequiredArgsConstructor
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final LDAPAuthenticationProvider ldapAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(req -> req.anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .usernameParameter("username")
                        .defaultSuccessUrl("/").permitAll())
                .logout(logout -> logout.logoutUrl("/perform_logout"))
                .rememberMe(rememberMe -> rememberMe.tokenValiditySeconds(30 * 24 * 3600).rememberMeCookieName("someKeyToRemember"));
    }

    // 表单生效需求加上这个, 跟SecurityConfig一样
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用的是jdbc来存储下面的两个用户
        auth.authenticationProvider(ldapAuthenticationProvider);
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/error/**", "/h2-console/**", "/public/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
