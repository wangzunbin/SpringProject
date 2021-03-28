package com.wangzunbin.uaa.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * ClassName:ExceptionHandler  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/3/28 16:33   <br/>
 */

@ControllerAdvice
public class ExceptionHandler implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        // 当为true的时候, 是把堆栈的信息返回, 当为false的时候, 不返回堆栈信息(生产环境需要)
        return Boolean.TRUE;
    }
}
