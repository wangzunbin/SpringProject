package com.wangzunbin.uaa.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * ClassName:LoginSecurityConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/3/28 17:53
 */
@Configuration
@Order(100)
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(req -> req.anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .usernameParameter("username")
                        .defaultSuccessUrl("/").permitAll())
                .logout(logout -> logout.logoutUrl("perform_logout"))
                .rememberMe(rememberMe -> rememberMe.tokenValiditySeconds(30 * 24 * 3600).rememberMeCookieName("someKeyToRemember"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/error/**", "/h2-console/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
