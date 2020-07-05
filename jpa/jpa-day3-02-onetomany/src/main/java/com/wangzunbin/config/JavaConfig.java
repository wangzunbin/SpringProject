package com.wangzunbin.config;

import org.springframework.context.annotation.Configuration;

/**
 * ClassName:JavaConfig  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/5 18:43   <br/>
 */

@Configuration
public class JavaConfig {

    @Configuration
    public class FilterConfig {

        /**
         * 解决hibernate懒加载出现的no session问题
         * @return
         */
        //    @Bean
        //    public FilterRegistrationBean filterRegistrationBean() {
        //        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //        filterRegistrationBean.setFilter(new OpenSessionInViewFilter());
        //        filterRegistrationBean.addInitParameter("urlPatterns", "/*");
        //        return filterRegistrationBean;
        //    }

        /**
         * 解决jpa 懒加载出现的no session问题
         *
         * @return
         */
//        @Bean
//        public FilterRegistrationBean filterRegistrationBean() {
//            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//            filterRegistrationBean.setFilter(new OpenEntityManagerInViewFilter());
//            filterRegistrationBean.addInitParameter("urlPatterns", "/*");
//            return filterRegistrationBean;
//        }
    }

}
