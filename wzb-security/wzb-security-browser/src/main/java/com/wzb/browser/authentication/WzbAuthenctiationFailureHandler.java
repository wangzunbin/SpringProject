package com.wzb.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzb.browser.support.SimpleResponse;
import com.wzb.security.core.properties.LoginResponseType;
import com.wzb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:WzbAuthenctiationFailureHandler  <br/>
 * Function: 登录失败之后 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/6 11:33   <br/>
 */

@Component("wzbAuthenctiationFailureHandler")
@Slf4j
public class WzbAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 此类是把对象变成json输出
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("登录失败");
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginResponseType())) {
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            // 设置这个主要是charset=UTF-8
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
//        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(e));
        }else {
            // 默认使用浏览器的跳转, 也就是网页
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
