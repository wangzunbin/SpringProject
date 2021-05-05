package com.wangzunbin.uaa.security.oauth2;

import com.wangzunbin.uaa.domain.User;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

import lombok.val;

/**
 * ClassName:CustomTokenEnhancer
 * Function: CustomTokenEnhancer
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 21:53
 */

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        if (oAuth2Authentication != null && oAuth2Authentication.getPrincipal() instanceof User) {
            val user = (User) oAuth2Authentication.getPrincipal();
            additionalInfo.put("email", user.getEmail());
            additionalInfo.put("mobile", user.getMobile());
            additionalInfo.put("name", user.getName());
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        }
        return oAuth2AccessToken;
    }
}
