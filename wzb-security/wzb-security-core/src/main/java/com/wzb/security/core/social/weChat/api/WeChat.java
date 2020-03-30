package com.wzb.security.core.social.weChat.api;

/**
 * ClassName:WeChat  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 21:49   <br/>
 */
public interface WeChat {

    WeChatUserInfo getUserInfo(String openId);
}
