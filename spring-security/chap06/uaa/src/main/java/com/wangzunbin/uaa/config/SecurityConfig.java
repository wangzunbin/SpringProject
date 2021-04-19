package com.wangzunbin.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangzunbin.uaa.security.auth.ldap.LDAPAuthenticationProvider;
import com.wangzunbin.uaa.security.auth.ldap.LDAPUserRepo;
import com.wangzunbin.uaa.security.filter.RestAuthenticationFilter;
import com.wangzunbin.uaa.security.jwt.JwtFilter;
import com.wangzunbin.uaa.security.userdetails.UserDetailsPasswordServiceImpl;
import com.wangzunbin.uaa.security.userdetails.UserDetailsServiceImpl;
import com.wangzunbin.uaa.service.rolehierarchy.RoleHierarchyService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.Arrays;
import java.util.Collections;
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

    private final JwtFilter jwtFilter;

    private final Environment environment;

    private final RoleHierarchyService roleHierarchyService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers(req -> req.mvcMatchers("/authorize/**", "/admin/**", "/api/**"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exp -> exp
                .authenticationEntryPoint(securityProblemSupport)
                .accessDeniedHandler(securityProblemSupport))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/authorize/**").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
//                        .antMatchers("/api/**").hasRole("USER")
//                        .antMatchers("/api/users/**").access("hasRole('ADMIN') or hasRole('USER')")
//                        .antMatchers("/api/users/{username}").access("hasRole('ADMIN') or authentication.name.equals(#username)")
//                        .antMatchers("/api/users/{username}").access("hasRole('ADMIN') or @userService.isValidUser(authentication, #username)")
                        .antMatchers("/api/users/by-email/{email}").hasRole("USER")
                        .antMatchers("/api/users/manager").hasRole("MANAGER")
                        .anyRequest().authenticated())
                // 替换UsernamePasswordAuthenticationFilter
//                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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

    /**
     * 我们在 Spring Boot 中有几种其他方式配置 CORS
     * 参见 https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
     * Mvc 的配置方式见 WebMvcConfig 中的代码
     *
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许跨域访问的主机
        if (environment.acceptsProfiles(Profiles.of("prod"))) {
            configuration.setAllowedOrigins(Collections.singletonList("http://127.0.0.1:4001"));
        } else {
            configuration.setAllowedOrigins(Collections.singletonList("https://www.wangzunbin.com"));
        }
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("X-Authenticate");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(roleHierarchyService.getRoleHierarchyExpr());
        return roleHierarchy;
    }
}
