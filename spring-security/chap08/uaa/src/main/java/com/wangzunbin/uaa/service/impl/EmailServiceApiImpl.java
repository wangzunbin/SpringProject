package com.wangzunbin.uaa.service.impl;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.wangzunbin.uaa.service.IEmailService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@ConditionalOnProperty(prefix = "wzb.email-provider", name = "name", havingValue = "api")
@RequiredArgsConstructor
@Service
public class EmailServiceApiImpl implements IEmailService {

    private final SendGrid sendGrid;

    @Override
    public void send(String email, String msg) {
        val from = new Email("service@wzb.com");
        val subject = "Spring Security 登录验证码";
        val to = new Email(email);
        val content = new Content("text/plain", "验证码为:" + msg);
        val mail = new Mail(from, subject, to, content);
        val request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            if (response.getStatusCode() == 202) {
                log.info("邮件发送成功");
            } else {
                log.error(response.getBody());
            }
        } catch (IOException e) {
            log.error("请求发生异常 {}", e.getLocalizedMessage());
        }
    }
}
