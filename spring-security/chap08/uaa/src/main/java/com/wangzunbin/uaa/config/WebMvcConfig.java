package com.wangzunbin.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.passay.MessageResolver;
import org.passay.spring.SpringMessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:WebMvcConfig  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 20:25   <br/>
 */
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {

    private final MessageSource messageSource;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(
                new ProblemModule(),
                new ConstraintViolationProblemModule());
    }

    /**
     * 国际化
     * @return 返回spring国际化处理器
     */
    @Bean
    public MessageResolver messageResolver(){
        return new SpringMessageResolver(messageSource);
    }

    /**
     * 配置 Java Validation 使用国际化的消息资源
     *
     * @return LocalValidatorFactoryBean
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    /**
     * 静态资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**", "/resources/**")
                .addResourceLocations("/webjars/", "/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
        registry.addViewController("/oauth/error").setViewName("authorize_error");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
