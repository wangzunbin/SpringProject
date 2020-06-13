package com.wangzunbin.quartz.common.model;

/**
 * @version 0.1 2017-03-23 11:23
 */
public class JobAPIRequest {

	/**
	 * 任务ID
	 */
	private Object jobId;

	/**
	 * job spring使用的bean name
	 */
	private String beanName;

	/**
	 * 任务cron表达式
	 */
	private String cronExpression;

	/**
	 * 任务上下文，参数可以设置到这里
	 */
	private JobContext context;

	public JobAPIRequest() {
	}

	public JobAPIRequest(Object jobId, String beanName) {
		this.jobId = jobId;
		this.beanName = beanName;
	}

	public JobAPIRequest(Object jobId, String beanName, String cronExpression) {
		this.jobId = jobId;
		this.beanName = beanName;
		this.cronExpression = cronExpression;
	}

	public JobContext getContext() {
		return context;
	}

	public void setContext(JobContext context) {
		this.context = context;
	}

	public Object getJobId() {
		return jobId;
	}

	public void setJobId(Object jobId) {
		this.jobId = jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}
