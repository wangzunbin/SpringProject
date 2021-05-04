package com.wangzunbin.uaa.service.impl;

import com.wangzunbin.uaa.service.ISmsService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:DefaultSendServiceAliSmsImpl
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/11 19:27
 */

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "wzb.sms-provider", name = "name", havingValue = "default")
@Service
public class DefaultSmsService implements ISmsService {

    @Override
    public void send(String mobile, String code) {
        log.debug("默认发送方式..., 手机号码: {}, 验证码: {}", mobile, code);
    }
}
