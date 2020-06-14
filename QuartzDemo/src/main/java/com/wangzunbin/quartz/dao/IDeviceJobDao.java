package com.wangzunbin.quartz.dao;

import com.wangzunbin.quartz.bean.DeviceJob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName:DeviceJobDao  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/14 11:04   <br/>
 */

@Repository
public interface IDeviceJobDao extends JpaRepository<DeviceJob, Long> {


    DeviceJob findById(Long id);

    @Modifying
    @Transactional
    void deleteByDeviceId(Long device);

    List<DeviceJob> findByDeviceId(Long deviceId);

    DeviceJob findFirstByDeviceId(Long deviceId);

    DeviceJob findFirstByDeviceIdOrderByUpdateTimeAsc(Long deviceId);
}
