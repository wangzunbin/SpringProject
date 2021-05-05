package com.wangzunbin.uaa.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:TotpVerificationDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 23:19
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotpVerificationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String mfaId;

    @NotBlank
    private String code;
}
