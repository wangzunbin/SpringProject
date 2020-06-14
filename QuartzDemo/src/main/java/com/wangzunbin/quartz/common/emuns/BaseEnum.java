package com.wangzunbin.quartz.common.emuns;

/**
 * 枚举基础接口
 *
 * @author ZhangJunhua
 * @version $Id: BaseEnum.java, v 0.1 2015-12-1 下午2:59:44 ZhangJunhua Exp $
 */
public interface BaseEnum<K> {

    /**
     * 获得枚举码
     * @return 枚举码
     */
    K getCode();

    /**
     * 获得该枚举的描述
     * @return 枚举描述
     */
    String getDesc();
}
