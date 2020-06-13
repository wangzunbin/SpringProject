package com.wangzunbin.quartz.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

/**
 * String to Date 类型转换
 */
public class DateConverter implements Converter<String, Date> {

    private static Pattern datePattern = null;
    private static Pattern dateTimePattern = null;

    @PostConstruct
    public void init() {
        datePattern = Pattern.compile("^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$");
        dateTimePattern = Pattern.compile("^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)) ([01]?[0-9]|2[0-3]):[0-5]?[0-9]:[0-5]?[0-9]$");
    }

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        if (datePattern.matcher(source).matches()) {
            return DateFormatUtil.parseYmd(source);
        }
        if (dateTimePattern.matcher(source).matches()) {
            return DateFormatUtil.parseYmdhms(source);
        }
        return null;
    }
}
