package com.wzb.security.core.validate.code;


import com.wzb.security.core.properties.SecurityConstants;

/**
 * ClassName:ValidateCodeType  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 16:50   <br/>
 */
public enum ValidateCodeType {

    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    public abstract String getParamNameOnValidate();
}
