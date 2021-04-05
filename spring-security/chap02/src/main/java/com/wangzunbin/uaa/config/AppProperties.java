package com.wangzunbin.uaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

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

    @Getter
    @Setter
    @Valid
    private Ali ali = new Ali();

    @Getter
    @Setter
    @Valid
    private SmsProvider smsProvider = new SmsProvider();

    @Getter
    @Setter
    @Valid
    private LeanCloud leanCloud = new LeanCloud();

    @Getter
    @Setter
    @Valid
    private EmailProvider emailProvider = new EmailProvider();

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

    @Getter
    @Setter
    public static class Ali {
        private String apiKey;
        private String apiSecret;
    }

    @Getter
    @Setter
    public static class SmsProvider {
        private String name;
        private String apiUrl;
    }

    @Getter
    @Setter
    public static class LeanCloud {
        private String appId;
        private String appKey;
    }

    @Getter
    @Setter
    public static class EmailProvider {
        private String name;
        private String apiKey;
    }
}
