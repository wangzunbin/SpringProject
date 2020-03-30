package com.wzb.security.core.properties;

import lombok.Data;

/**
 * ClassName:SocialProperties  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 22:56   <br/>
 */
@Data
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeChatProperties weChat = new WeChatProperties();
}
