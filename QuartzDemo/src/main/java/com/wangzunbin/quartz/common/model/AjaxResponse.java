package com.wangzunbin.quartz.common.model;


import com.wangzunbin.quartz.common.emuns.RespCode;

/**
 * AJAX请求的 JSON类型的返回值结果数据对象
 */
public final class AjaxResponse {

    /** 成功 */
    public static final AjaxResponse SUCCESS          = new AjaxResponse(RespCode.SUCCESS);

    /** 失败 */
    public static final AjaxResponse FAILURE          = new AjaxResponse(RespCode.FAILED);

    /** 系统错误 */
    public static final AjaxResponse ERROR            = new AjaxResponse(RespCode.ERROR);

    /** 参数错误 */
    public static final AjaxResponse ILLEGAL_ARGUMENT = new AjaxResponse(RespCode.ILLEGAL_ARGUMENT);

    private int                      code;                                                          // 状态码
    private String                   msg;                                                           // 返回消息
    private Object                   data;                                                          // 返回数据

    private AjaxResponse() {
    }

    private AjaxResponse(RespCode code) {
        this.code = code.getCode();
        this.msg = code.getDesc();
    }

    private AjaxResponse(RespCode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }

    private AjaxResponse(RespCode code, Object data) {
        this.code = code.getCode();
        this.msg = code.getDesc();
        this.data = data;
    }

    public static AjaxResponse success() {
        return SUCCESS;
    }

    public static AjaxResponse success(Object data) {
        return new AjaxResponse(RespCode.SUCCESS, data);
    }

    public static AjaxResponse success(RespCode code, Object data) {
        return new AjaxResponse(code, data);
    }

    public static AjaxResponse failure() {
        return FAILURE;
    }

    public static AjaxResponse failure(String msg) {
        return new AjaxResponse(RespCode.FAILED, msg);
    }

    public static AjaxResponse failure(RespCode code) {
        return new AjaxResponse(code);
    }

    public static AjaxResponse failure(RespCode code, String msg) {
        return new AjaxResponse(code, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
