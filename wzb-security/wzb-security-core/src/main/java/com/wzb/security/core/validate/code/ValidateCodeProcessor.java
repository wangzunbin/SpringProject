package com.wzb.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * ClassName:ValidateCodeProcessor  <br/>
 * Funtion: 校验码处理器，封装不同校验码的处理逻辑 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 16:46   <br/>
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);
}
