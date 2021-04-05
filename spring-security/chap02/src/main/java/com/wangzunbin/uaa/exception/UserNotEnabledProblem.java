package com.wangzunbin.uaa.exception;

import com.wangzunbin.uaa.util.Constants;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

/**
 * ClassName:UserNotEnabledProblem
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 22:41
 */
public class UserNotEnabledProblem extends AbstractThrowableProblem {
    private static final URI TYPE = URI.create(Constants.PROBLEM_BASE_URI + "/user-not-enabled");

    public UserNotEnabledProblem() {
        super(
                TYPE,
                "未授权访问",
                Status.UNAUTHORIZED,
                "用户未激活，请联系管理员");
    }
}
