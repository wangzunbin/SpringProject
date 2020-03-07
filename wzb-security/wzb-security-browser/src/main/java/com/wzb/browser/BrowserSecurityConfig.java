package com.wzb.browser;

import com.wzb.browser.authentication.WzbAuthenctiationFailureHandler;
import com.wzb.browser.authentication.WzbAuthenticationSuccessHandler;
import com.wzb.security.core.properties.SecurityProperties;
import com.wzb.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * ClassName:BrowserSecurityConfig  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/4 9:54   <br/>
 */

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private WzbAuthenticationSuccessHandler wzbAuthenticationSuccessHandler;

    @Autowired
    private WzbAuthenctiationFailureHandler wzbAuthenctiationFailureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;


    /**
     * 实现记住我的功能
     *
     * @return 操作JDBC的模板
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(Boolean.TRUE);
        return tokenRepository;
    }


    /**
     * 你要登陆论坛，输入用户名张三，密码1234，密码正确，证明你张三确实是张三，这就是 authentication；
     * 再一check用户张三是个版主，所以有权限加精删别人帖，这就是 authorization。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        validateCodeFilter.afterPropertiesSet();
        http// 表单登陆
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //这个是去到一个页面, 输入用户名和密码
                .formLogin()
                //告诉spring先跳到这个页面
                .loginPage("/authentication/require")
                // 告诉spring security去/authentication/require"再去UsernamePasswordAuthenticationFilter认证
                .loginProcessingUrl("/authentication/form")
                // 登录成功
                .successHandler(wzbAuthenticationSuccessHandler)
                // 登录失败
                .failureHandler(wzbAuthenctiationFailureHandler)
                // 这个是弹框输入用户名和密码
                //                .httpBasic()
                .and()
                // 记住我的功能(RememberMeAuthenticationFilter: 当前面的过滤器没有效果不行的时候, 它会调用此过滤器)
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                // 对下面请求的授权
                .authorizeRequests()
                // 下面的地址不需要身份认证
                // 下面这个error要加上, 不然这个页面会要求认证
                .antMatchers("/authentication/require",
                        "/error",
                        "/code/image",
                        "/code/sms",
                        securityProperties.getBrowser().getLoginPage()
                ).permitAll()
                // 剩下任何请求都需要认证
                .anyRequest()
                //  都需要身份认证
                .authenticated()
                .and()
                // 暂时把跨域关掉
                .csrf().disable();
//        第一版本
   /*     http// 表单登陆
                //这个是去到一个页面, 输入用户名和密码
                .formLogin()
                .loginPage("/wzb-login.html")
                // 告诉spring security去UsernamePasswordAuthenticationFilter认证
                .loginProcessingUrl("/authentication/form")
                // 这个是弹框输入用户名和密码
//                .httpBasic()
                .and()
                // 对下面请求的授权
                .authorizeRequests()
                // 下面的地址不需要身份认证
                .antMatchers("/wzb-login.html").permitAll()
                // 剩下任何请求都需要认证
                .anyRequest()
                //  都需要身份认证
                .authenticated()
                .and()
                // 暂时把跨域关掉
                .csrf().disable();*/
    }
}
