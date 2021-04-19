package com.wangzunbin.uaa.exception;

import com.wangzunbin.uaa.util.Constants;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

/**
 * ClassName:DataConflictProblem
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 21:37
 */

public class DataConflictProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create(Constants.PROBLEM_BASE_URI + "/data-conflict");

    public DataConflictProblem(String msg) {
        super(
                TYPE,
                "数据验证失败",
                Status.CONFLICT,
                msg);
    }
}
