package com.wangzunbin.quartz.job;


import com.wangzunbin.quartz.bean.DeviceJob;
import com.wangzunbin.quartz.common.model.JobContext;
import com.wangzunbin.quartz.common.service.IJobExecute;
import com.wangzunbin.quartz.service.IDeviceJobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务层中调用定时任务
 * 
 * @author zhb
 * @version v 0.1 2017-03-31 19:06
 */
@Component
@Slf4j
public class DevicePowerJob implements IJobExecute {


    @Autowired
    private IDeviceJobService deviceJobService;

//    @Autowired
//    private IDeviceCtrlService deviceCtrlService;

    @Override
    public void exec(Object jobId, JobContext context) {
        try {
            Long id = (Long) jobId;
            DeviceJob deviceJob = deviceJobService.findById(id);
            log.info("指定的quartz Job执行了, {}", id);
            if (deviceJob == null){
                log.error("找不到这个任务, jobId={}", jobId);
                return;
            }
//            deviceCtrlService.ctrlPower(deviceJob.getDeviceId(), deviceJob.getOp());
            //如果是执行一次的任务,在执行完毕后关闭关闭该任务
            String repeat = deviceJob.getRepeat();
            if ("0".equals(repeat)){
                deviceJob.setEnable(false);
                deviceJobService.update(deviceJob);
            }
        } catch (Exception e) {
            log.error("execute DevicePowerJob failure, jobId={}, error={}", jobId, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
