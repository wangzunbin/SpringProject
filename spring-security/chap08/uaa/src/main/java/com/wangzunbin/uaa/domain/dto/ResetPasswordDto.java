package com.wangzunbin.uaa.domain.dto;

import com.wangzunbin.uaa.domain.MfaType;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:ResetPasswordDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 21:45
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto implements Serializable {

 private static final long serialVersionUID = 1L;

 private String emailOrMobile;

 private MfaType mfaType;
}
