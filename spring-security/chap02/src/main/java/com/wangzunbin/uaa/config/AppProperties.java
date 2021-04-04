package com.wangzunbin.uaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * ClassName:AppProperties
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 22:25
 */
@Configuration
@ConfigurationProperties(prefix = "wzb")
public class AppProperties {

    @Getter
    @Setter
    private Jwt jwt = new Jwt();

    @Setter
    @Getter
    public static class Jwt {
        private String header = "Authorization";
        private String prefix = "Bearer ";
        // access token 过期时间
        private Long accessTokenExpireTime = 60_000L;
        // refresh token 过期时间
        private Long refreshTokenExpireTime = 30 * 24 * 3600 * 1000L;

    }
}
