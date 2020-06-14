package com.wangzunbin.quartz;

import com.wangzunbin.quartz.bean.DeviceJob;
import com.wangzunbin.quartz.dao.IDeviceJobDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:DeviceJobDaoTest  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/14 11:14   <br/>
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-test.xml"})
@Slf4j
public class IDeviceJobDaoTest {

    @Autowired
    private IDeviceJobDao deviceJobDao;

    @Test
    public void testSaveOrUpdateDeviceJobDao() throws Exception {
        DeviceJob deviceJob = DeviceJob.builder()
                //                .id(1L)
                .userId(3L)
                .op(Boolean.TRUE)
                .hour(1)
                .minute(30)
                                .repeat("1,3,5")
                .cron("1")
                .enable(Boolean.FALSE)
                .createTime(new Date())
                .updateTime(new Date())
                .deviceId(2L)
                .deviceName("王尊斌信号机")
                .master(Boolean.TRUE)
                .build();

        deviceJobDao.save(deviceJob);
        log.info("保存, {}", deviceJob.toString());
    }


    @Test
    public void testQueryDeviceJobDao() throws Exception {
        DeviceJob deviceJob = deviceJobDao.findById(1L);
        log.info(deviceJob.toString());
    }

    @Test
    public void testUpdateDeviceJobDao() throws Exception {
        DeviceJob deviceJob = deviceJobDao.findById(1L);
        log.info(deviceJob.toString());
        DeviceJob newDeviceJob = DeviceJob.builder()
                .updateTime(new Date())
                .deviceId(3L)
                .build();
        BeanUtil.copyProperties(newDeviceJob, deviceJob, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        log.info(deviceJob.toString());
        deviceJobDao.save(deviceJob);
    }

    @Test
    public void deleteDeviceJob() throws Exception {
//        deviceJobDao.delete(2L);
        deviceJobDao.deleteByDeviceId(2L);
    }

    @Test
    public void testQueryListByDeviceId() throws Exception{
        List<DeviceJob> deviceJobs = deviceJobDao.findByDeviceId(2L);
        log.info(deviceJobs.toString());
    }

    @Test
    public void testQueryOneByDeviceId() throws Exception{
        DeviceJob deviceJob = deviceJobDao.findFirstByDeviceIdOrderByUpdateTimeAsc(2L);
        log.info(deviceJob.toString());
    }
}
