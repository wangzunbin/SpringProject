package com.wangzunbin.uaa.service.impl;

import com.wangzunbin.uaa.service.ISmsService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

import cn.leancloud.sms.AVSMS;
import cn.leancloud.sms.AVSMSOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@RequiredArgsConstructor
@Slf4j
@Service
@ConditionalOnProperty(prefix = "wzb.sms-provider", name = "name", havingValue = "lean-cloud")
public class SmsServiceLeanCloudSmsImpl implements ISmsService {

    @Override
    public void send(String mobile, String msg) {
        val option = new AVSMSOption();
        option.setTtl(10);
        option.setApplicationName("王尊斌实战Spring Security");
        option.setOperation("两步验证");
        option.setTemplateName("登录验证");
        option.setSignatureName("王尊斌");
        option.setType(AVSMS.TYPE.TEXT_SMS);
        option.setEnvMap(Map.of("smsCode", msg));
        AVSMS.requestSMSCodeInBackground(mobile, option)
            .take(1)
            .subscribe(
                (res) -> log.info("短信发送成功 {}", res),
                (err) -> log.error("发送短信时产生服务端异常 {}", err.getLocalizedMessage())
            );
    }
}
