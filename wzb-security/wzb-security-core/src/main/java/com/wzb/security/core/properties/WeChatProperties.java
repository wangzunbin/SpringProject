package com.wzb.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * ClassName:WeChatProperties  <br/>
 * Function: 微信的相关配置 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 21:44   <br/>
 */
public class WeChatProperties extends SocialProperties {

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weChat。
     */
    private String providerId = "weChat";

    /**
     * @return the providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
