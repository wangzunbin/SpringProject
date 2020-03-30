package com.wzb.security.core.social.weChat.connet;

import com.wzb.security.core.social.weChat.api.WeChat;
import com.wzb.security.core.social.weChat.api.WeChatUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * ClassName:WeChat  <br/>
 * Function: 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 21:56   <br/>
 */
public class WeChatAdapter implements ApiAdapter<WeChat> {

    private String openId;

    public WeChatAdapter() {}

    public WeChatAdapter(String openId){
        this.openId = openId;
    }

    /**
     * @param api
     * @return
     */
    @Override
    public boolean test(WeChat api) {
        return true;
    }

    /**
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(WeChat api, ConnectionValues values) {
        WeChatUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(WeChat api) {
        return null;
    }

    /**
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(WeChat api, String message) {
        //do nothing
    }
}
