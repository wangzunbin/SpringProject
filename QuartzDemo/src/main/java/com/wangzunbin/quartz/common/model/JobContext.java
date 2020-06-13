package com.wangzunbin.quartz.common.model;

import java.util.HashMap;

/**
 * 定时任务上下文
 * @version 0.1 2017-03-23 11:33
 */
public class JobContext extends HashMap<String, Object> {

	public static final String CK_TASK_ID = "taskid";

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public static JobContext create() {
		JobContext context = new JobContext();
		return context;
	}

	public JobContext addContext(String key, Object value) {
		this.put(key, value);
		return this;
	}

	public void dispose() {
		this.clear();
	}
}
