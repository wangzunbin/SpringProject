package com.wangzunbin.quartz.common.model;

/**
 * 定时任务上下文
 * @version 0.1 2017-03-23 11:31
 */

public class JobAPIResult {

	public static final int RC_SUCCESS = 0;
	public static final int RC_EXCEPTION = 500;

	/**
	 * 调用结果错误码
	 */
	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public JobAPIResult(int resultCode) {
		this.resultCode = resultCode;
	}

	public static JobAPIResult createSuccess() {
		return new JobAPIResult(RC_SUCCESS);
	}
}
