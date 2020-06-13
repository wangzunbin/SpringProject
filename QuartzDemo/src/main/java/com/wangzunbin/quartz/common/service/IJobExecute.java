package com.wangzunbin.quartz.common.service;


import com.wangzunbin.quartz.common.model.JobContext;

/**
 * 注明任务执行接口
 * @version 0.1 2017-03-23 11:53
 */
public interface IJobExecute {

	/**
	 * 执行任务的接口
	 * @param jobId 任务的ID，这块使用各个业务自己的ID
	 * @param context 任务上下文，需要用到额外参数放到这个里面
	 */
	void exec(Object jobId, JobContext context);
}
