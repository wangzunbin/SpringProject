package com.wzb.security.core.validate.code;

import com.wzb.security.core.validate.code.image.ImageCodeGenerator;
import com.wzb.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.wzb.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:ValidateCodeBeanConfig  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 12:54   <br/>
 */

@Configuration
public class ValidateCodeBeanConfig {

    @Bean
    // 找到imageCodeGenerator就用下面的创建了,比如说在demo模块里面找到imageCodeGenerator,就用不会用默认的
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        return new ImageCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
