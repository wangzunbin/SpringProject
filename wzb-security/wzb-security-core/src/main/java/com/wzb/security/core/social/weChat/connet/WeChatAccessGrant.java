package com.wzb.security.core.social.weChat.connet;

import org.springframework.social.oauth2.AccessGrant;

/**
 * ClassName:WeChatAccessGrant  <br/>
 * Function:  <br/>
 * 微信的access_token信息。与标准OAuth2协议不同，微信在获取access_token时会同时返回openId,并没有单独的通过accessToke换取openId的服务
 *
 * 所以在这里继承了标准AccessGrant，添加了openId字段，作为对微信access_token信息的封装。
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 21:56   <br/>
 */
public class WeChatAccessGrant extends AccessGrant {

    /**
     *
     */
    private static final long serialVersionUID = -7243374526633186782L;

    private String openId;

    public WeChatAccessGrant() {
        super("");
    }

    public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    /**
     * @return the openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId the openId to set
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
