package com.wangzunbin.uaa.integration.passay;

import org.passay.AbstractMessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:PassayPropertiesMessageResolver
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 19:36
 */

@RequiredArgsConstructor
public class PassayPropertiesMessageResolver extends AbstractMessageResolver {
    private final MessageSource messageSource;

    @Override
    protected String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
