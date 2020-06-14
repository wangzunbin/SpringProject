package com.wangzunbin.quartz.common.emuns;


/**
 * @version 0.1 2017-03-16 17:57
 */
public enum RespCode implements BaseEnum<Integer> {
    SUCCESS(0, null), ERROR(-1, "系统错误"), FAILED(1, "失败"),

    /** 参数错误 */
    ILLEGAL_ARGUMENT(2, "参数错误"),

    /** 验证码错误 */
    CAPTCHA_ERROR(3, "验证码错误"),

    /** 验证码失效 */
    CAPTCHA_LAPSED(4, "验证码失效"),

    /** 用户未登录 */
    NOT_LOGIN(11, "未登录"),
    /** 登录账号信息已过期 */
    TOKEN_EXPIRE(12, "登录已失效"),
    /** 该用户已被禁用！ */
    USER_DISABLED(13, "该用户已被禁用"),

    /** 未注册 */
    NOT_REGISTER(111, "未注册"),

    USER_NOT_EXIST(112, "该用户不存在！"),

    USER_EXIST(113, "用户已存在,请直接登录！"),
    PHONE_NOT_BIND(114, "未绑定手机号！"),
    USER_NOT_PASSWORD(115, "用户没有密码！"),

    /** 重复点赞 */
    USER_LIKE_ALREADY(131, "今天已经赞TA了"),
    /** 不能给自己点赞 */
    USER_LIKE_MYSELF(132, "不要给自己点赞哟"),

    /** 设备不在线 */
    DEVICE_OFFLINE(200, "设备不在线"),
    /** 请不要频繁控制设备 */
    DEVICE_CTRL_OFTEN(220, "请不要频繁控制设备"),
    /** 未绑定该设备 */
    DEVICE_CTRL_NOT_BIND(221, "未绑定该设备"),
    /** 您不是主管理员 */
    DEVICE_IS_NOT_ADMIN(222, "您不是主管理员"),
    /** 设备名重复 */
    DEVICE_ALREADY_NAME(223, "设备名重复"),
    /** 设备名重复 */
    DEVICE_NOT_MASTER(224, "设备无人使用"),
    /** 设备未响应 */
    DEVICE_CTRL_TIMEOUT(201, "设备未响应"),
    /** 开盖保护 */
    DEVICE_CTRL_PROTECT(202, "设备保护盖未关闭"),
    /** 控制失败 */
    DEVICE_CTRL_FAILURE(203, "控制失败"),
    /** 设备未开启 */
    DEVICE_NOT_RUNNING(205, "设备未开启"),
    /** 设备不存在 */
    DEVICE_NOT_EXIST(207, "设备不存在"),
    /**
     * 当前设备尚有其他用户绑定，请先转让'主管理员'权限后方可直接删除设备
     */
    STATUS_209(209, "当前设备尚有其他用户绑定，请先转让'主管理员'权限后方可直接删除设备"),

    /** 该设备已绑定 */
    DEVICE_ALREADY_BIND(210, "该设备已绑定"),

    /** 未获取到设备位置 */
    DEVICE_POSITION_NOT_EXIST(231, "未获取到设备位置"),

    /** 已经是最新版本 */
    VERSION_IS_NEW(300, "已经是最新版本"),

    /** 版本号不存在 */
    VERSION_NOT_EXIST(301, "版本号不存在"),

    /** 智能家居控制失败 */
    SMART_CONTROL_FAILURE(401, "控制失败"),

    /** 智能家居您不是主管理员 */
    SMARTHOME_IS_NOT_ADMIN(310, "您不是主管理员"),

    /** 重复绑定相同的家庭 */
    SMARTHOME_EXIT_BINDED(311, "重复绑定相同的家庭"),

    /** 用户拥有的家不能超过5个 */
    SMARTHOME_HOMENUMBER_LIMIT(312, "用户拥有的家不能超过5个"),

    /** 此家庭已经存在房间 */
    SMARTHOME_EXIT_ROOM(313, "此家庭已经存在房间");


    private Integer code;
    private String desc;

    RespCode(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static RespCode getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        RespCode[] enumValues = values();
        for (RespCode enumValue : enumValues) {
            if (enumValue.getCode().equals(code)) {
                return enumValue;
            }
        }
        return null;
    }
}
