package com.wzb.security.core.properties;

import lombok.Data;

/**
 * ClassName:ImageCodeProperties  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 11:39   <br/>
 */

@Data
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }

    private int width = 67;
    private int height = 23;

    private String url;
}
