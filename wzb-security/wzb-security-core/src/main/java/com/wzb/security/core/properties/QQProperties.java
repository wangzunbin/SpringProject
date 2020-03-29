package com.wzb.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * ClassName:QQProperties  <br/>
 * Function: QQProperties <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 22:44   <br/>
 */
public class QQProperties extends SocialProperties {

    // 服务提供商唯一标识
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
