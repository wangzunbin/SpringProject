package com.wangzunbin.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangzunbin.uaa.security.UaaSuccessHandler;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static org.springframework.security.config.Customizer.withDefaults;

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
                // 认证
                .authorizeRequests(req -> req.anyRequest().authenticated())
                // 登录页(不加上permitAll这个, 会出现不停地重定向)
                .formLogin(form -> form.loginPage("/login")
                        // 设置登录的用户字段, 跟
//                        .usernameParameter("username")
                        .defaultSuccessUrl("/")
                        .successHandler(new UaaSuccessHandler())
                        .failureHandler(jsonLoginFailureHandler())
                        .permitAll())
                // 展示登录弹框
//                .httpBasic(withDefaults())
                .csrf(withDefaults())
                .logout(logout -> logout.logoutUrl("/perform_logout").permitAll());
//                .rememberMe()

    }

    private AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
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
}
