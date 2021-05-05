package com.wangzunbin.uaa.validator;

import com.wangzunbin.uaa.annotation.ValidPassword;
import com.wangzunbin.uaa.util.CryptoUtil;

import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * ClassName:PasswordValidator  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:41   <br/>
 */


@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {


    private final MessageResolver messageResolver;
    private final CryptoUtil cryptoUtil;

    @Override
    public void initialize(final ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        val validator = new PasswordValidator(messageResolver, cryptoUtil.getPasswordRules());
        val result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.join(",", validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}
