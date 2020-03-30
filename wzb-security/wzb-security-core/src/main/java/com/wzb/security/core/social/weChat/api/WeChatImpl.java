package com.wzb.security.core.social.weChat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ClassName:WeChatImpl  <br/>
 * Function: Weixin API调用模板， scope为Request的Spring bean, 根据当前用户的accessToken创建。 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 21:51   <br/>
 */
public class WeChatImpl extends AbstractOAuth2ApiBinding implements WeChat {

    /**
     *
     */
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    /**
     * @param accessToken
     */
    public WeChatImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
     */
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return messageConverters;
    }

    /**
     * 获取微信用户信息。
     */
    @Override
    public WeChatUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String response = getRestTemplate().getForObject(url, String.class);
        if(StringUtils.contains(response, "errcode")) {
            return null;
        }
        WeChatUserInfo profile = null;
        try {
            profile = objectMapper.readValue(response, WeChatUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }
}
