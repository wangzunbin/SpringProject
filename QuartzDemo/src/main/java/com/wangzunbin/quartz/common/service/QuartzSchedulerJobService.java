package com.wangzunbin.quartz.common.service;


import com.wangzunbin.quartz.common.model.JobAPIRequest;
import com.wangzunbin.quartz.common.model.JobAPIResult;
import com.wangzunbin.quartz.common.model.JobContext;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * QUARTZ 定时任务实现
 * @version 0.1 2017-03-23 11:34
 */
@Service
public class QuartzSchedulerJobService implements ISchedulerJobService {

	public static final String BEAN_NAME = "beanName";
	public static final String JOB_ID = "jobId";
	public static final String JOB_CONTEXT = "job_context";
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Scheduler scheduler;

	@PostConstruct
	public void init() throws SchedulerException {
		scheduler.start();
	}

	private JobKey createJobKey(JobAPIRequest request) {
		Object jobId = request.getJobId();
		String beanName = request.getBeanName();
		JobKey jobKey = JobKey.jobKey(beanName + ":" + jobId, beanName);
		return jobKey;
	}


	private TriggerKey createTriggerKey(JobAPIRequest request) {
		Object jobId = request.getJobId();
		String beanName = request.getBeanName();
		TriggerKey triggerKey = TriggerKey.triggerKey(beanName + ":" + jobId, beanName);
		return triggerKey;
	}

	@Override
	public JobAPIResult start(JobAPIRequest request) {
		JobAPIResult apiResult = JobAPIResult.createSuccess();
		JobKey jobKey = createJobKey(request);
		TriggerKey triggerKey = createTriggerKey(request);
		JobDetail jobDetail = JobBuilder.newJob().withIdentity(jobKey).ofType(DelegatingSpringJob.class).build();
		String cronExpression = request.getCronExpression();
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(BEAN_NAME, request.getBeanName());
		dataMap.put(JOB_ID, request.getJobId());
		dataMap.put(JOB_CONTEXT, request.getContext());
		/*
		withMisfireHandlingInstructionDoNothing
		——不触发立即执行
		——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
		withMisfireHandlingInstructionIgnoreMisfires
		——以错过的第一个频率时间立刻开始执行
		——重做错过的所有频率周期后
		——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
		withMisfireHandlingInstructionFireAndProceed(默认)
		——以当前时间为触发频率立刻触发一次执行
		——然后按照Cron频率依次执行
		*/
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionFireAndProceed())
				.usingJobData(dataMap)
				.build();
		try {
			//如果任务存在，则更新任务
			if (scheduler.checkExists(jobKey)) {
				CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
				scheduler.rescheduleJob(triggerKey, trigger);
				logger.info("rescheduleJob jobKey=[{}] exists!update tigger[{}]---->[{}]", new Object[]{jobKey, oldTrigger.getCronExpression(), cronExpression});
			} else {
				scheduler.scheduleJob(jobDetail, trigger);
				logger.info("scheduleJob jobKey=[{}] tigger[{}]", new Object[]{jobKey, cronExpression});
			}
		} catch (SchedulerException e) {
			logger.error(e.getLocalizedMessage(), e);
			apiResult.setResultCode(JobAPIResult.RC_EXCEPTION);
		}
		return apiResult;
	}

	@Override
	public JobAPIResult stop(JobAPIRequest request) {
		JobAPIResult apiResult = JobAPIResult.createSuccess();
		TriggerKey tiggerKey = createTriggerKey(request);
		try {
			logger.info("stop job tiggerKey[{}]", tiggerKey);
			boolean found = scheduler.unscheduleJob(tiggerKey);
		} catch (SchedulerException e) {
			logger.error(e.getLocalizedMessage(), e);
			apiResult.setResultCode(JobAPIResult.RC_EXCEPTION);
		}
		return apiResult;
	}

	@Override
	public JobAPIResult pause(JobAPIRequest request) {
		JobAPIResult apiResult = JobAPIResult.createSuccess();
		TriggerKey tiggerKey = createTriggerKey(request);
		try {
			logger.info("pause job tiggerKey[{}]", tiggerKey);
			scheduler.pauseTrigger(tiggerKey);
		} catch (SchedulerException e) {
			logger.error(e.getLocalizedMessage(), e);
			apiResult.setResultCode(JobAPIResult.RC_EXCEPTION);
		}
		return apiResult;
	}

	@Override
	public JobAPIResult resume(JobAPIRequest request) {
		JobAPIResult apiResult = JobAPIResult.createSuccess();
		TriggerKey tiggerKey = createTriggerKey(request);
		try {
			logger.info("resume job tiggerKey[{}]", tiggerKey);
			scheduler.resumeTrigger(tiggerKey);
		} catch (SchedulerException e) {
			logger.error(e.getLocalizedMessage(), e);
			apiResult.setResultCode(JobAPIResult.RC_EXCEPTION);
		}
		return apiResult;
	}

	@Override
	public JobAPIResult delete(JobAPIRequest request) {
		JobAPIResult apiResult = JobAPIResult.createSuccess();
		JobKey jobKey = createJobKey(request);
		try {
			logger.info("delete job jobKey[{}]", jobKey);
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			logger.error(e.getLocalizedMessage(), e);
			apiResult.setResultCode(JobAPIResult.RC_EXCEPTION);
		}
		return apiResult;
	}

	public static class DelegatingSpringJob implements Job {
		private Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			JobContext jobContext = null;
			try {
				JobDataMap jobDataMap = context.getMergedJobDataMap();
				String beanName = jobDataMap.getString(BEAN_NAME);
				Object jobId = jobDataMap.get(JOB_ID);
				jobContext = (JobContext) jobDataMap.get(JOB_CONTEXT);
				ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
				IJobExecute execute = applicationContext.getBean(beanName, IJobExecute.class);
				if (execute == null) {
					logger.warn("========bean[{}] not implements IJobExecute or not exists!skip job execute=========", beanName);
					return;
				} else {
					if (jobContext == null) {
						jobContext = new JobContext();
					}
					Object taskId = createTaskId(jobId, beanName, jobContext);
					jobContext.put(JobContext.CK_TASK_ID, taskId);
					long start = System.currentTimeMillis();
					logger.info("job[{}] start...", taskId);
					execute.exec(jobId, jobContext);
					logger.info("job[{}] end...used {} second", taskId, (System.currentTimeMillis() - start) / 1000);
				}
			} catch (SchedulerException e) {
				logger.error(e.getLocalizedMessage(), e);
			} finally {
				if (jobContext != null) {
					jobContext.dispose();
				}
			}
		}

		private static Object createTaskId(Object jobId, String beanName, JobContext jobContext) {
			long currentTimeMillis = System.currentTimeMillis();
			return String.format("%s_%s_%s", beanName, jobId, currentTimeMillis);
		}
	}
}
