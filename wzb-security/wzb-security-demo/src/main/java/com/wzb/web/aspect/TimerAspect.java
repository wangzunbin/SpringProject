package com.wzb.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.util.Date;

/**
 * ClassName:TimerAspect  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 23:35   <br/>
 */

//@Aspect
//@Component
public class TimerAspect {

    @Around("execution(* com.wzb.web.controller.UserController.*(..))")
    public Object handleController(ProceedingJoinPoint point) throws Throwable {
        System.out.println("timer aspect start");
        long startTime = new Date().getTime();
        Object proceed = point.proceed();
        System.out.println("timer aspect: " + (new Date().getTime() - startTime));
        System.out.println("timer aspect end");
        return null;
    }
}
