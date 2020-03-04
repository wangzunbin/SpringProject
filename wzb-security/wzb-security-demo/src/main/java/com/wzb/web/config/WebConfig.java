package com.wzb.web.config;

import com.wzb.web.filter.TimerFilter;
import com.wzb.web.interceptor.TimerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:WebConfig  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 23:12   <br/>
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TimerInterceptor interceptor;

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 异步请求的拦截器的配置
//        configurer.registerCallableInterceptors(interceptor);
//        configurer.setDefaultTimeout()
//        configurer.setTaskExecutor()
    }

    /**
     *  这个拦截器是拦截同步的请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }

    @Bean
    public FilterRegistrationBean timerFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimerFilter timerFilter = new TimerFilter();
        registrationBean.setFilter(timerFilter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }
}
