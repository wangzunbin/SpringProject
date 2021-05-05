package com.wangzunbin.uaa.service;

import com.wangzunbin.uaa.service.email.IEmailService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * ClassName:EmailTest
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 18:04
 */
@ActiveProfiles("prod")
@SpringBootTest
public class EmailTest {

    @Autowired
    private IEmailService emailService;

    @Test
    void testSendSmtp() {
        emailService.send("905192187@qq.com", "测试发送邮箱逻辑");
    }
}
