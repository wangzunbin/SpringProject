package com.wzb.validator;

import com.wzb.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ClassName:MyConstraintValidator  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 22:22   <br/>
 */

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {


    @Autowired
    private IHelloService helloService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("my validator init");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        helloService.greeting(String.valueOf(o));
        System.out.println(o);
        return Boolean.FALSE;
    }


}
