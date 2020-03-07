package com.wzb.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * ClassName:QQProperties  <br/>
 * Funtion: QQProperties <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 22:44   <br/>
 */
public class QQProperties extends SocialProperties {

    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
