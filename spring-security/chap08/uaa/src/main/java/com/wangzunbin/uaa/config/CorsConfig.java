package com.wangzunbin.uaa.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * ClassName:CorsConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 17:17
 */
@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("X-Authenticate");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // 在使用 OAUTH Authorization Server 上，必须设置成最高优先级，否则 OAUTH2 Filter 会直接先拦截请求，使 CORS 设置失效
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}