package com.wangzunbin.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangzunbin.uaa.security.auth.ldap.LDAPAuthenticationProvider;
import com.wangzunbin.uaa.security.auth.ldap.LDAPUserRepo;
import com.wangzunbin.uaa.security.filter.RestAuthenticationFilter;
import com.wangzunbin.uaa.security.userdetails.UserDetailsPasswordServiceImpl;
import com.wangzunbin.uaa.security.userdetails.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * ClassName:SecurityConfig  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/22 23:34   <br/>
 */

@Slf4j
//@EnableWebSecurity(debug = true)
@Configuration
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
@Order(99)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    private final SecurityProblemSupport securityProblemSupport;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserDetailsPasswordServiceImpl userDetailsPasswordServiceImpl;

    private final LDAPUserRepo ldapUserRepo;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers(req -> req.mvcMatchers("/authorize/**", "/admin/**", "/api/**"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exp -> exp
                .authenticationEntryPoint(securityProblemSupport)
                .accessDeniedHandler(securityProblemSupport))
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/authorize/**").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/**").hasRole("USER")
                        .anyRequest().authenticated())
                // 替换UsernamePasswordAuthenticationFilter
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                // 用basic认证
                .httpBasic(Customizer.withDefaults());

    }
    private RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter(objectMapper);
        filter.setAuthenticationSuccessHandler(jsonLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(jsonLoginFailureHandler());
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/authorize/login");
        return filter;
    }


    private AuthenticationSuccessHandler jsonLoginSuccessHandler() {
        return (request, response, authentication) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().println(objectMapper.writeValueAsString(authentication));
            log.debug("认证成功");
        };
    }

    private AuthenticationFailureHandler jsonLoginFailureHandler() {
        return (req, res, exp) -> {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            val errData = Map.of(
                    "title", "认证失败",
                    "details", exp.getMessage()
            );
            res.getWriter().println(objectMapper.writeValueAsString(errData));
        };
    }

    // api生效, 跟LoginSecurityConfig一样
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用的是jdbc来存储下面的两个用户
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(ldapAuthenticationProvider());
    }

    @Bean
    LDAPAuthenticationProvider ldapAuthenticationProvider(){
        return new LDAPAuthenticationProvider(ldapUserRepo);
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        val daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsPasswordService(userDetailsPasswordServiceImpl); // 配置密码自动升级服务
        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new MessageDigestPasswordEncoder();
        val idForDefault = "bcrypt";
        var encoders = Map.of(
                idForDefault, new BCryptPasswordEncoder(),
                "SHA-1", new MessageDigestPasswordEncoder("SHA-1")
        );
        return new DelegatingPasswordEncoder(idForDefault, encoders);
    }
}
