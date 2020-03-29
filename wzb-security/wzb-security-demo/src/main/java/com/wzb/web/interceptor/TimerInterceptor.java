package com.wzb.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * ClassName:TimerInterceptor  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 23:20   <br/>
 */

// 拦截器不能知道入参等一些信息, 这个时候需要用aop配合使用
@Component
public class TimerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        request.setAttribute("startTime", new Date().getTime());
//        System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
//        System.out.println(((HandlerMethod)handler).getMethod().getName());
        return Boolean.TRUE;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long startTime = (Long) request.getAttribute("startTime");
        System.out.println("timer interceptor 耗时: " + (new Date().getTime() - startTime));
    }

    // 有异常执行下面这个方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        Long startTime = (Long) request.getAttribute("startTime");
        System.out.println("afterCompletion interceptor 耗时: " + (new Date().getTime() - startTime));
    }
}
