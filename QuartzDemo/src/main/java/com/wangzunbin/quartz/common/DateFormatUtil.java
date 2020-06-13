package com.wangzunbin.quartz.common;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Date处理工具类
 *
 */
@Data
@Slf4j
public class DateFormatUtil {

    public static final SimpleDateFormat               YM             = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat               YM_noSpli       = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat               YMd            = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat               YMd_noSpli     = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat               YMd_cn         = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat               YMdhm_cn       = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    public static final SimpleDateFormat               YMdhm          = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat               YMdhms         = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat               YMdhms_noSpli  = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat               YMdhmsS        = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat               YMdhmsS_noSpli = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String formatYmd(){
        return format(YMd, new Date());
    }

    public static String formatYmd(Date date){
        return format(YMd, date);
    }

    /**
     * @return 按(yyyy-MM-dd HH:mm:ss)格式化当前时间
     */
    public static String formatYmdhms(){
        return format(YMdhms, new Date());
    }

    /**
     * @return 按(yyyy-MM-dd HH:mm:ss)格式化Date
     */
    public static String formatYmdhms(Date date){
        return format(YMdhms, date);
    }

    public static String format(DateFormat dateFormat){
        return format(dateFormat, new Date());
    }

    public static String format(DateFormat dateFormat, Date date){
        if(dateFormat == null || date == null){
            return "";
        }
        return dateFormat.format(date);
    }

    public static Date parseYmd(String dateString) {
        return parse(dateString, YMd);
    }

    public static Date parseYmdhms(String dateString) {
        return parse(dateString, YMdhms);
    }

    public static Date parse(String dateString, DateFormat dateFormat) {
        if(dateFormat == null || StringUtils.isBlank(dateString)){
            return null;
        }
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            log.warn("Parse date failure! dateString=["+dateString+"]", e);
            return null;
        }
    }
}