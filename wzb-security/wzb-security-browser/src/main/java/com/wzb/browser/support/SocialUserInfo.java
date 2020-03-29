package com.wzb.browser.support;

import lombok.Data;

/**
 * ClassName:SocialUserInfo  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 0:03   <br/>
 */

@Data
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headImg;
}
