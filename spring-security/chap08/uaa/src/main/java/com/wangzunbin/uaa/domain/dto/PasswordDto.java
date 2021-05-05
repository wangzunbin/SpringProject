package com.wangzunbin.uaa.domain.dto;

import com.wangzunbin.uaa.annotation.ValidPassword;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:PasswordDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 21:44
 */

@Data
public class PasswordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String oldPassword;

    @ValidPassword
    private String newPassword;
}
