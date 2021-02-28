package com.wangzunbin.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangzunbin.uaa.security.filter.RestAuthenticationFilter;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 替换UsernamePasswordAuthenticationFilter
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/authorize/**").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/**").hasRole("USER")
                        .anyRequest().authenticated())
                // 登录页(不加上permitAll这个, 会出现不停地重定向)
//                .formLogin(form -> form.loginPage("/login")
//                        // 设置登录的用户字段, 跟
////                        .usernameParameter("username")
//                        .defaultSuccessUrl("/")
//                        .successHandler(new UaaSuccessHandler())
//                        .failureHandler(jsonLoginFailureHandler())
//                        .permitAll())
                // 展示登录弹框
//                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
//                .logout(logout -> logout.logoutUrl("/perform_logout").permitAll());
//                .rememberMe()

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

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/public/**", "/", "/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("12345678"))
                .roles("USER", "ADMIN");
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
