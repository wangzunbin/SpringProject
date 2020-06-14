package com.wangzunbin.quartz.service.impl;

import com.wangzunbin.quartz.bean.DeviceJob;
import com.wangzunbin.quartz.common.model.JobAPIRequest;
import com.wangzunbin.quartz.dao.IDeviceJobDao;
import com.wangzunbin.quartz.helper.DeviceHelper;
import com.wangzunbin.quartz.service.IDeviceJobService;
import com.wangzunbin.quartz.service.ISchedulerJobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 设备定时任务信息Service实现类
 *
 * @author 代码生成器
 * @version 0.1 2017-03-31 14:38:02
 */
@Service
public class DeviceJobServiceImpl implements IDeviceJobService {


    @Autowired
    private IDeviceJobDao deviceJobDao;

    @Autowired
    private ISchedulerJobService quartzSchedulerJobService;

    private static final String DEVICE_JOB_CLASS_NAME = "devicePowerJob";
    private static final String DEVICE_PREPARE_CLASS_NAME = "devicePrepareJob";

    private static final String CACHE_NAME = "device_job";

//    @Autowired
//    private IDeviceCtrlService deviceCtrlService;
//
//    @Autowired
//    private DeviceStatusCache deviceStatusCache;

    @Transactional
//    @Caching(evict = {@CacheEvict(value = CACHE_NAME, key = "'findByDeviceId:'+#deviceJob.deviceId")})
    @Override
    public Long saveAndStartJob(DeviceJob deviceJob) {
        // 生成cron表达式
        String cron = DeviceHelper.createCron(deviceJob);

        deviceJob.setCreateTime(new Date());
        deviceJob.setUpdateTime(deviceJob.getCreateTime());
        deviceJob.setCron(cron);

        deviceJob.setUserId(1L);
        deviceJob.setEnable(true);
        deviceJobDao.save(deviceJob);


        JobAPIRequest jobAPIRequest = new JobAPIRequest(deviceJob.getId(), DEVICE_JOB_CLASS_NAME, deviceJob.getCron());
        quartzSchedulerJobService.start(jobAPIRequest);
        return deviceJob.getId();
    }

    /** 生成不重复任务的cron表达式 */
    private String buildOneCron(DeviceJob deviceJob) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, deviceJob.getHour());
        c.set(Calendar.MINUTE, deviceJob.getMinute());
        Date time = c.getTime();

        // 需要顺延到最近的时间 就是加一天
        if (time.before(new Date())) {
            c.add(Calendar.DATE, 1);
            return DeviceHelper.dateToCron(c.getTime());
        } else {
            return DeviceHelper.dateToCron(time);
        }
    }

    @Transactional
    @Override
    public void deleteWithJob(Long id, Long deviceId) {
        deviceJobDao.delete(id);
        // 同时删除任务
        JobAPIRequest jobAPIRequest = new JobAPIRequest(id, DEVICE_JOB_CLASS_NAME);
        quartzSchedulerJobService.delete(jobAPIRequest);
    }


    @Override
    public void cleanCache(Long deviceId) {
    }

    @Transactional
    @Override
    public void updateWithJob(DeviceJob deviceJob) {

        // 更新或者关闭任务


        // 生成cron表达式
        String cron = DeviceHelper.createCron(deviceJob);
        deviceJob.setCron(cron);
        deviceJob.setUserId(1L);
        deviceJob.setUpdateTime(new Date());
        deviceJob.setCreateTime(new Date());
        deviceJob.setEnable(true);
        deviceJobDao.save(deviceJob);

        DeviceJob newJob = deviceJobDao.findById(deviceJob.getId());
        //如果调用转换时间方法得到null,表明该任务是重复任务
        Date date1 = DeviceHelper.cronToDate(newJob.getCron());
        if (date1 != null) {  //如果返回时间表明用户的任务只执行一次,
            newJob.setCron(buildOneCron(newJob));
        }
        JobAPIRequest jobAPIRequest = new JobAPIRequest(newJob.getId(), DEVICE_JOB_CLASS_NAME, newJob.getCron());
        quartzSchedulerJobService.start(jobAPIRequest);
    }

    @Override
    public List<DeviceJob> findByDeviceId(Long deviceId) {
//        Params params = Params.create("deviceId", deviceId);
        return deviceJobDao.findByDeviceId(deviceId);
    }


    @Override
    public void changEnable(Long id, Long deviceId) {

//        DeviceJob deviceJob = new DeviceJob();
//
//        deviceJob.setId(id);
        DeviceJob svrDeviceJob = deviceJobDao.findById(id);
        svrDeviceJob.setEnable(!svrDeviceJob.getEnable());
        svrDeviceJob.setUpdateTime(new Date());
        deviceJobDao.save(svrDeviceJob);

        if (svrDeviceJob.getEnable()) {
            String cron = buildOneCron(svrDeviceJob);
            JobAPIRequest jobAPIRequest = new JobAPIRequest(svrDeviceJob.getId(), DEVICE_JOB_CLASS_NAME, cron);
            quartzSchedulerJobService.resume(jobAPIRequest);
        } else {
            JobAPIRequest jobAPIRequest = new JobAPIRequest(svrDeviceJob.getId(), DEVICE_JOB_CLASS_NAME);
            quartzSchedulerJobService.pause(jobAPIRequest);
        }
    }

    @Override
    public DeviceJob findById(Long id) {
        return deviceJobDao.findOne(id);
    }

    @Override
    public void update(DeviceJob deviceJob) {
        DeviceJob deviceJob1 = deviceJobDao.findById(deviceJob.getId());
        deviceJobDao.save(deviceJob1);
    }

    /**
     * 添加预净化任务
     */
//    @Caching(evict = {@CacheEvict(value = CACHE_NAME, key = "'findByDeviceId:'+#deviceId")})
//    @Override
//    public Date prepareJob(long deviceId, Integer minute) {
//        // 执行预净化
//        CommandResponse response = deviceCtrlService.asyncCtrlFan(deviceId, 4);
//        if (response != CommandResponse.SUCCESS) {
//            return null;
//        }
//
//        // 执行预净化之后的任务
//        Date endTime = DateUtils.addMinutes(new Date(), minute);
//        String cron = DeviceHelper.dateToCron(endTime);
//        JobAPIRequest apiRequest = new JobAPIRequest(deviceId, DEVICE_PREPARE_CLASS_NAME, cron);
//        quartzSchedulerJobService.start(apiRequest);
//
//        deviceStatusCache.setPrep(deviceId, minute, endTime);
//
//        DevicePushHelper.pushPrep(deviceId, minute, endTime);
//        return endTime;
//    }

//    /**
//     * 删除预净化任务
//     */
//    @Caching(evict = {@CacheEvict(value = CACHE_NAME, key = "'findByDeviceId:'+#deviceId")})
//    @Override
//    public void removePrepareJob(long deviceId) {
//        String prepMins = deviceStatusCache.getPrepMins(deviceId);
//        if (prepMins == null) {
//            return;
//        }
//
//        deviceStatusCache.removePrepare(deviceId);
//
//        DevicePushHelper.pushPrep(deviceId, 0, null);
//
//        quartzSchedulerJobService.delete(new JobAPIRequest(deviceId, DEVICE_PREPARE_CLASS_NAME));
//    }
//
//    /**
//     * 更新预净化任务
//     */
//    @Caching(evict = {@CacheEvict(value = CACHE_NAME, key = "'findByDeviceId:'+#deviceJob.deviceId")})
//    @Override
//    public void update(DeviceJob deviceJob) {
//        dao.update(deviceJob);
//    }
}
