package com.wangzunbin.uaa.security.oauth2;

import com.wangzunbin.uaa.domain.User;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

import lombok.val;

/**
 * ClassName:CustomTokenConverter
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 21:52
 */
public class CustomTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            val additionalInfo = new HashMap<String, Object>();
            val user = (User) authentication.getPrincipal();
            additionalInfo.put("email", user.getEmail());
            additionalInfo.put("mobile", user.getMobile());
            additionalInfo.put("name", user.getName());
            ((DefaultOAuth2AccessToken) accessToken)
                    .setAdditionalInformation(additionalInfo);
        }
        val token = super.enhance(accessToken, authentication);
        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(new HashMap<>());
        return token;
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return super.extractAuthentication(map);
    }
}