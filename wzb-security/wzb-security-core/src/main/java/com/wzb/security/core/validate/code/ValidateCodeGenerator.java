package com.wzb.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * ClassName:ValidateCodeGenerator  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 12:49   <br/>
 */
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);

}
