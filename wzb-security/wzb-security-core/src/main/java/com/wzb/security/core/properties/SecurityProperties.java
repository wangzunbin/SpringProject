package com.wzb.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ClassName:SecurityProperties  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/4 12:09   <br/>
 */
@ConfigurationProperties(prefix = "wzb.security")
@Data
public class SecurityProperties {

    private BrowserProperties browserProperties = new BrowserProperties();


}
