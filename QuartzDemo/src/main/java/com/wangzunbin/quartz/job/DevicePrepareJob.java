package com.wangzunbin.quartz.job;


import com.wangzunbin.quartz.common.model.JobAPIRequest;
import com.wangzunbin.quartz.common.model.JobContext;
import com.wangzunbin.quartz.common.service.IJobExecute;
import com.wangzunbin.quartz.service.ISchedulerJobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 执行预净化任务
 * @version 0.1 2017-04-22 11:51
 */
@Component
@Slf4j
public class DevicePrepareJob implements IJobExecute {



    @Autowired
    private ISchedulerJobService quartzSchedulerJobService;



    @Override
    public void exec(Object jobId, JobContext context) {
        try {
            Long deviceId = (Long) jobId;
            // 读取设备的缓存信息
            log.info("DevicePrepareJob执行了, {}", deviceId);
            // 在执行之后删除本任务
            JobAPIRequest apiRequest = new JobAPIRequest(jobId, "devicePrepareJob");
            quartzSchedulerJobService.delete(apiRequest);
        } catch (Exception e) {
            log.error("execute DevicePrepareJob error, jobId={}, error={}", jobId, e.getLocalizedMessage());
        }
    }
}
