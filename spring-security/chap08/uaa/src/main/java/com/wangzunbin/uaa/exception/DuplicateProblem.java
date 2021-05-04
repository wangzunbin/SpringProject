package com.wangzunbin.uaa.exception;


import com.wangzunbin.uaa.util.Constants;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class DuplicateProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create(Constants.PROBLEM_BASE_URI + "/duplicate");

    public DuplicateProblem(String msg) {
        super(
                TYPE,
                "发现重复数据",
                Status.CONFLICT,
                msg);
    }
}
