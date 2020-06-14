package com.wangzunbin.quartz.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.wangzunbin.quartz.helper.BooleanToBitConverter;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 设备定时任务信息
 *
 * @author 代码生成器
 * @version 0.1 2017-03-31 14:38:02
 */

@Entity
@Table(name = "signal_device_job")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@ToString
public class DeviceJob {

    private static final long serialVersionUID = 400187491411046400L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Convert(converter= BooleanToBitConverter.class)
    private Boolean op;                                    // 操作类型(
    // 1=开机 ,
    @Min(0)                                                // 0=关机)
    @Max(23)
    private Integer hour;
    @Min(0)
    @Max(59)
    private Integer minute;

    private String repeat;                                // 任务重复策略（“”=只运行一次，1-7表示周一到周日，多个用,分割）

    private String cron;                                  // cron时间表达式

    @Convert(converter= BooleanToBitConverter.class)
    private Boolean enable;                                // 是否启用

    @JSONField(serialize = false)
    @Column(name = "create_time")
    private Date createTime;                            // 创建时间

    @JSONField(serialize = false)
    @Column(name = "update_time")
    private Date updateTime;                            // 更新时间

    @Column(name = "device_id")
    private Long deviceId;                              // 设备id

    @Transient
    private String deviceName;                            // 设备名

    @Transient
    private Boolean master;                                          //是否为主管理员


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getOp() {
        return op;
    }

    public void setOp(Boolean op) {
        this.op = op;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }
}