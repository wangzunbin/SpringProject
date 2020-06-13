package com.wangzunbin.quartz.common.service;


import com.wangzunbin.quartz.common.model.JobAPIRequest;
import com.wangzunbin.quartz.common.model.JobAPIResult;

/**
 * 定时任务服务接口
 * @version 0.1 2017-03-23 11:33
 */
public interface ISchedulerJobService {
	/**
	 * 启动任务
	 */
	JobAPIResult start(JobAPIRequest request);

	/**
	 * 停止任务
	 */
	JobAPIResult stop(JobAPIRequest request);

	/**
	 * 暂停任务
	 */
	JobAPIResult pause(JobAPIRequest request);

	/**
	 * 恢复任务
	 */
	JobAPIResult resume(JobAPIRequest request);

	/**
	 * 删除任务
	 */
	JobAPIResult delete(JobAPIRequest request);
}
