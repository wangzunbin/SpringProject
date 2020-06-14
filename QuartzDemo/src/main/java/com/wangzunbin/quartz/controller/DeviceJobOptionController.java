package com.wangzunbin.quartz.controller;


import com.wangzunbin.quartz.bean.DeviceJob;
import com.wangzunbin.quartz.common.model.AjaxResponse;
import com.wangzunbin.quartz.common.model.Params;
import com.wangzunbin.quartz.service.IDeviceJobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 设备定时任务信息Controller
 *
 * @author 代码生成器
 * @version 0.1 2017-03-31 14:38:02
 */
@RequestMapping(value = "/device/job/option")
@Controller
public class DeviceJobOptionController {

    @Autowired
    private IDeviceJobService deviceJobService;



    // 添加一个定时任务

    @RequestMapping(value = "/save.json", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse saveAndStartJob(@Valid DeviceJob deviceJob, BindingResult bindingResult) {

        // 参数校验
        if (bindingResult.hasErrors()) {
            return AjaxResponse.ILLEGAL_ARGUMENT;
        }
        Long id = deviceJobService.saveAndStartJob(deviceJob);

        return AjaxResponse.success(Params.create("id", id));
    }

    // 用户更新定时任务
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse update(@Valid DeviceJob deviceJob, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors() || deviceJob.getId() == null) {
            return AjaxResponse.ILLEGAL_ARGUMENT;
        }

        DeviceJob old = deviceJobService.findById(deviceJob.getId());
        if(old == null){
            return AjaxResponse.ILLEGAL_ARGUMENT;
        }

//        Long deviceId = deviceJob.getDeviceId();
//        if(deviceId == null){
//            deviceId = old.getDeviceId();
//        }
//        DeviceInfo deviceInfo = deviceCacheService.getDeviceInfo(deviceId);
//        if(deviceInfo == null || !Objects.equals(deviceInfo.getUserId(), LoginUserHolder.getUserId())){
//            return AjaxResponse.failure(RespCode.DEVICE_IS_NOT_ADMIN);
//        }

//        deviceJobService.cleanCache(old.getDeviceId());
        deviceJobService.updateWithJob(deviceJob);
        return AjaxResponse.SUCCESS;
    }

    // 用户删除任务
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse delete(@RequestParam Long id) {
        DeviceJob deviceJob = deviceJobService.findById(id);
        if(deviceJob == null){
            return AjaxResponse.ILLEGAL_ARGUMENT;
        }

        // 检查是否为主管理员
//        AjaxResponse x = checkMaster(deviceJob.getDeviceId());
//        if (x != null) return x;

        deviceJobService.deleteWithJob(id, deviceJob.getDeviceId());
        return AjaxResponse.SUCCESS;
    }

    //关闭或者开启一个任务
    @RequestMapping(value = "/enable.json", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse changeEnable(@RequestParam Long id) {
        DeviceJob deviceJob = deviceJobService.findById(id);
        if(deviceJob == null){
            return AjaxResponse.ILLEGAL_ARGUMENT;
        }

        // 检查是否为主管理员
//        AjaxResponse x = checkMaster(deviceJob.getDeviceId());
//        if (x != null) return x;

        deviceJobService.changEnable(id, deviceJob.getDeviceId());
        return AjaxResponse.SUCCESS;
    }

    /**
     * 检查是否为主管理员
     */
//    private AjaxResponse checkMaster(Long deviceId) {
//        DeviceInfo deviceInfo = deviceCacheService.getDeviceInfo(deviceId);
//        if(deviceInfo == null){
//            return AjaxResponse.failure(RespCode.DEVICE_NOT_EXIST);
//        }
//        if(!Objects.equals(deviceInfo.getUserId(), LoginUserHolder.getUserId())){
//            return AjaxResponse.failure(RespCode.DEVICE_IS_NOT_ADMIN);
//        }
//        return null;
//    }
}
