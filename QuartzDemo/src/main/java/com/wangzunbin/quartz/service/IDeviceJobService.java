package com.wangzunbin.quartz.service;


import com.wangzunbin.quartz.bean.DeviceJob;

import java.util.List;

/**
 * 设备定时任务信息Service接口
 * 
 * @author 代码生成器
 * @version 0.1 2017-03-31 14:38:02
 */
public interface IDeviceJobService {

    Long saveAndStartJob(DeviceJob deviceJob);

    void deleteWithJob(Long id,Long deviceId);

    /**
     * 清除缓存
     */
    void cleanCache(Long deviceId);

    void updateWithJob(DeviceJob deviceJob);

    /**
     * 查询一个设备的所有任务
     */
    List<DeviceJob> findByDeviceId(Long deviceId);

    /**
     *  改变任务的开启和关闭状态
     * @param id
     */
    void changEnable(Long id, Long deviceId);

    DeviceJob findById(Long id);

    void update(DeviceJob job);

    //    /**
//     * 预净化任务
//     */
//    Date prepareJob(long deviceId, Integer minute);
//
//    /**
//     * 删除预净化任务
//     */
//    void removePrepareJob(long deviceId);
//
//
//    void update(DeviceJob deviceJob);
}