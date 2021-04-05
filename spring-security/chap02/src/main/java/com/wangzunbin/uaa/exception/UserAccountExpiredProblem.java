package com.wangzunbin.uaa.exception;

import com.wangzunbin.uaa.util.Constants;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

/**
 * ClassName:UserAccountExpiredProblem
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 22:42
 */
public class UserAccountExpiredProblem  extends AbstractThrowableProblem {
    private static final URI TYPE = URI.create(Constants.PROBLEM_BASE_URI + "/user-locked");

    public UserAccountExpiredProblem() {
        super(
                TYPE,
                "未授权访问",
                Status.UNAUTHORIZED,
                "用户账户已过期，请联系管理员");
    }
}
