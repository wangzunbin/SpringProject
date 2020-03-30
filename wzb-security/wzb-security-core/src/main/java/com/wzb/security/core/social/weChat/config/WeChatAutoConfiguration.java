package com.wzb.security.core.social.weChat.config;

import com.wzb.security.core.properties.SecurityProperties;
import com.wzb.security.core.properties.WeChatProperties;
import com.wzb.security.core.social.WzbConnectView;
import com.wzb.security.core.social.weChat.connet.WeChatConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * ClassName:WeChatAutoConfiguration  <br/>
 * Function: 微信登录配置 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 21:53   <br/>
 */

@Configuration
@ConditionalOnProperty(prefix = "wzb.security.social.weChat", name = "app-id")
public class WeChatAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
     * #createConnectionFactory()
     */
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeChatProperties weChatProperties = securityProperties.getSocial().getWeChat();
        return new WeChatConnectionFactory(weChatProperties.getProviderId(), weChatProperties.getAppId(),
                weChatProperties.getAppSecret());
    }

    @Bean({"connect/weChatConnect", "connect/weChatConnected"})
    @ConditionalOnMissingBean(name = "weCHatConnectedView")
    public View wzbConnectedView() {
        return new WzbConnectView();
    }



}
