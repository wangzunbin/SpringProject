package com.wangzunbin.uaa.exception;

import com.wangzunbin.uaa.util.Constants;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

/**
 * ClassName:InvalidTotpProblem
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 23:18
 */
public class InvalidTotpProblem extends AbstractThrowableProblem {
    private static final URI TYPE = URI.create(Constants.PROBLEM_BASE_URI + "/invalid-token");

    public InvalidTotpProblem() {
        super(
                TYPE,
                "验证码错误",
                Status.UNAUTHORIZED,
                "验证码不正确或已过期，请重新输入");
    }
}
