package com.wzb.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * ClassName:TimerFilter  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 23:05   <br/>
 */
//@Component
public class TimerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("timer filter init");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("timer filter start");
        long startTime = new Date().getTime();
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String requestURI = req.getRequestURI();
        if (requestURI.contains("favicon.ico")) {
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("timer filter: " + (new Date().getTime() - startTime));
        System.out.println("timer filter finish");
    }

    @Override
    public void destroy() {
        System.out.println("timer filter destroy");
    }
}
