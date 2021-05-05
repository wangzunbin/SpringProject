package com.wangzunbin.uaa.exception;

import com.wangzunbin.uaa.util.Constants;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

/**
 * ClassName:BadCredentialProblem
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 11:13
 */
public class BadCredentialProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create(Constants.PROBLEM_BASE_URI + "/bad-credentials");

    public BadCredentialProblem() {
        super(
                TYPE,
                "认证失败",
                Status.UNAUTHORIZED,
                "用户名或密码错误");
    }
}
