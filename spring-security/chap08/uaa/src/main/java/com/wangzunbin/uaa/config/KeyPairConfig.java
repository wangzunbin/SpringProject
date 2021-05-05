package com.wangzunbin.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * ClassName:KeyPairConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/4 23:13
 */

@Configuration
public class KeyPairConfig {

    @Bean
    public KeyPair keyPair() throws Exception {
        ClassPathResource ksFile = new ClassPathResource("wzb-jwt.jks");
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, "wzb-pass".toCharArray());
        return ksFactory.getKeyPair("wzb-oauth-jwt");
    }
}
