package com.wangzunbin.uaa.util;

import org.springframework.stereotype.Component;

import java.util.Random;

import lombok.val;

/**
 * ClassName:CryptoUtil
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 22:03
 */

@Component
public class CryptoUtil {

    public String randomAlphanumeric(int targetStringLength) {
        int leftLimit = 48; // 数字 '0'
        int rightLimit = 122; // 字母 'z'
        val random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // 过滤掉 Unicode 65 和 90 之间的字符
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
