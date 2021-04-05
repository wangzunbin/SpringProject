package com.wangzunbin.uaa.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AliConfig {

    private final AppProperties appProperties;

    @Bean
    public IAcsClient iAcsClient() {
        DefaultProfile profile = DefaultProfile.getProfile(
            "cn-hangzhou",
            appProperties.getAli().getApiKey(),
            appProperties.getAli().getApiSecret());
        return new DefaultAcsClient(profile);
    }
}
