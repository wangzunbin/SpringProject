package com.wangzunbin.uaa.service.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.wangzunbin.uaa.config.AppProperties;
import com.wangzunbin.uaa.service.sms.ISmsService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wzb.sms-provider", name = "name", havingValue = "ali")
@Service
public class SmsServiceAliSmsImpl implements ISmsService {

    private final IAcsClient client;
    private final AppProperties appProperties;

    @Override
    public void send(String mobile, String msg) {
        val request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(appProperties.getSmsProvider().getApiUrl());
        request.setSysAction("SendSms");
        request.setSysVersion("2017-05-25");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "医典通");
        request.putQueryParameter("TemplateCode", "SMS_123666918");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" +
            msg +
            "\",\"product\":\"王尊斌的Spring Security\"}");
        try {
            val response = client.getCommonResponse(request);
            log.info("短信发送结果 {}", response.getData());
        } catch (ServerException e) {
            log.error("发送短信时产生服务端异常 {}", e.getLocalizedMessage());
        } catch (ClientException e) {
            log.error("发送短信时产生客户端异常 {}", e.getLocalizedMessage());
        }
    }
}
