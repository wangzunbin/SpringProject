package com.wangzunbin.uaa.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.val;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void initialize(final ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateEmail(username));
    }

    private boolean validateEmail(final String email) {
        val pattern = Pattern.compile(EMAIL_PATTERN);
        val matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
