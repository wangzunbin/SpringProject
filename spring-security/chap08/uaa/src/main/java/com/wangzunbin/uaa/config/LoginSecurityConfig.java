package com.wangzunbin.uaa.config;


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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .key("someSecret")
                        .tokenValiditySeconds(86400))
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated());
    }


}
