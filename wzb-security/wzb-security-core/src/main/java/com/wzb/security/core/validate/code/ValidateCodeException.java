package com.wzb.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * ClassName:ValidateCodeException  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 9:39   <br/>
 */

public class ValidateCodeException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
