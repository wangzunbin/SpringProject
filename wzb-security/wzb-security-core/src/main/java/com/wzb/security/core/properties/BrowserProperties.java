package com.wzb.security.core.properties;

import lombok.Data;

/**
 * ClassName:BrowserProperties  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/4 12:09   <br/>
 */

@Data
public class BrowserProperties {

    private String loginPage = "/wzb-login.html";

    // 默认是返回JSON语句
    private LoginResponseType loginResponseType = LoginResponseType.JSON;
}
