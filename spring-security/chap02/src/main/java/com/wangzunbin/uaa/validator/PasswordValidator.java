package com.wangzunbin.uaa.validator;

import com.wangzunbin.uaa.annotation.ValidPassword;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.WhitespaceRule;
import org.passay.spring.SpringMessageResolver;

import java.util.Arrays;

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
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final SpringMessageResolver springMessageResolver;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        val validator = new org.passay.PasswordValidator(springMessageResolver,
                Arrays.asList(
                        new LengthRule(8, 30),
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                        new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
                        new WhitespaceRule()
                ));
        val result = validator.validate(new PasswordData(password));
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(String.join(",", validator.getMessages(result)))
                .addConstraintViolation();
        return result.isValid();
    }
}
