package com.wangzunbin.uaa.validator;


import com.wangzunbin.uaa.domain.dto.RegisterDto;
import com.wangzunbin.uaa.domain.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final RegisterDto obj, final ConstraintValidatorContext context) {
        return obj.getPassword().equals(obj.getMatchingPassword());
    }
}
