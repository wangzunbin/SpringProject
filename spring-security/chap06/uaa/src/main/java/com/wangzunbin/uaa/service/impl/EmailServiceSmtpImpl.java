package com.wangzunbin.uaa.service.impl;

import com.wangzunbin.uaa.service.IEmailService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.val;

@ConditionalOnProperty(prefix = "wzb.email-provider", name = "name", havingValue = "smtp")
@RequiredArgsConstructor
@Service
public class EmailServiceSmtpImpl implements IEmailService {

    private final JavaMailSender emailSender;

    @Override
    public void send(String email, String msg) {
        val message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("service@wzb.com");
        message.setSubject("Spring Security 登录验证码");
        message.setText("验证码为:" + msg);
        emailSender.send(message);
    }
}
