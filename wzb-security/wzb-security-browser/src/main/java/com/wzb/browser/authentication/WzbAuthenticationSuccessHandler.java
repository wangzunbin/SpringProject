package com.wzb.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzb.security.core.properties.LoginResponseType;
import com.wzb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:WzbAuthenticationSuccessHandler  <br/>
 * Funtion: 认证成功 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/6 11:24   <br/>
 */
@Component("wzbAuthenticationSuccessHandler")
@Slf4j
//public class WzbAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class WzbAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginResponseType())) {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            // 默认使用浏览器的跳转, 也就是网页
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);

        }
    }
}
