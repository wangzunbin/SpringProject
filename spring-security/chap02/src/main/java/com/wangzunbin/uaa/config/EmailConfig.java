package com.wangzunbin.uaa.config;

import com.sendgrid.SendGrid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class EmailConfig {

    private final AppProperties appProperties;

    @ConditionalOnProperty(prefix = "wzb.email-provider", name = "api-key")
    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(appProperties.getEmailProvider().getApiKey());
    }
}
