package com.wangzunbin.quartz.helper;


import com.wangzunbin.quartz.bean.DeviceJob;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 定时任务的帮助类
 *
 * @version v 0.1 2017-03-31 18:37
 */
public class DeviceHelper {
    private static final Pattern PATTERN_MAC = Pattern.compile("^[0-9A-F]{12}$");

    /**
     * 检查mac地址是否正确
     */
    public static boolean validateMac(String mac) {
        if (mac == null) {
            return false;
        }
        return PATTERN_MAC.matcher(mac).matches();
    }

    /**
     * 清除接口里不需要返回的属性
     */
//    public static DeviceStatus cleanUselessAttr(DeviceStatus deviceStatus) {
//        if (deviceStatus == null) {
//            return null;
//        }
//        deviceStatus.setCcid(null);
//        deviceStatus.setVersion(null);
//        deviceStatus.setLed(null);
//        deviceStatus.setSmart(null);
//        return deviceStatus;
//    }

    /**
     * 生成cron表达式
     */
    public static String createCron(DeviceJob deviceJob) {
        StringBuilder cron;
        if ("0".equals(deviceJob.getRepeat())) {
            //执行一次的任务
            cron = new StringBuilder("0 ");
            cron.append(deviceJob.getMinute());
            cron.append(" ");
            cron.append(deviceJob.getHour());
            Calendar c = Calendar.getInstance();
            cron.append(" ");
            cron.append(c.get(Calendar.DAY_OF_MONTH));
            cron.append(" ");
            cron.append(c.get(Calendar.MONTH) + 1);
            cron.append(" ? ");
            cron.append(c.get(Calendar.YEAR));
        } else {
            // 执行多次的任务
            cron = new StringBuilder("0 ");
            cron.append(deviceJob.getMinute());
            cron.append(" ");
            cron.append(deviceJob.getHour());
            cron.append(" ? * ");
            cron.append(deviceJob.getRepeat());
        }
        return cron.toString();
    }

//    /**
//     * 解析设备返回的结果
//     *
//     * @param commandResponse
//     * @return
//     */
//    public static AjaxResponse parseCommond(CommandResponse commandResponse) {
//        switch (commandResponse) {
//            case SUCCESS:
//                return AjaxResponse.SUCCESS;
//            case OFFLINE:
//                return AjaxResponse.failure(RespCode.DEVICE_OFFLINE);
//            case TIMEOUT:
//                return AjaxResponse.failure(RespCode.DEVICE_CTRL_TIMEOUT);
//            case PROTECT:
//                return AjaxResponse.failure(RespCode.DEVICE_CTRL_PROTECT);
//            case FAILURE:
//                return AjaxResponse.failure(RespCode.DEVICE_CTRL_FAILURE);
//            default:
//                return AjaxResponse.ERROR;
//        }
//    }

    // 把cron表达式解析成时间
    public static Date cronToDate(String cron) {
        // 0 0 0 5 4 ? 2017
        String[] split = cron.split(" ");
        if (split[5].equals("?")) {
            try {
                cron = cron.replace("?", "");
                return DateUtils.parseDate(cron, "ss mm HH dd MM yy");
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }


    public static String dateToCron(Date date) {
        String cron = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(cron);
        return sdf.format(date);
    }

    /**
     * 是否为真实设备
     */
    public static boolean isRealDevice(Long deviceId) {
        if (deviceId == null) {
            return false;
        }
        // 设备id 10001-30000之间的为假数据
        if (deviceId > 10000 && deviceId < 30000) {
            return false;
        }
        return true;
    }

    public static boolean validateLocation(BigDecimal lon, BigDecimal lat) {
        if (lon == null || lat == null) {
            return false;
        }
        if (!(lon.compareTo(new BigDecimal("180")) < 0 && lon.compareTo(new BigDecimal("-180")) > 0)) {
            return false;
        }

        if (!(lat.compareTo(new BigDecimal("90")) < 0 && lat.compareTo(new BigDecimal("-90")) > 0)) {
            return false;
        }


        return true;
    }


    public static void main(String[] args) {

        BigDecimal lon = new BigDecimal("-118.22");
        BigDecimal lat = new BigDecimal("89.22");

        System.out.println(validateLocation(lon, lat));

    }

}
