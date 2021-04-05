package com.wangzunbin.uaa.domain.dto;

import com.wangzunbin.uaa.domain.MfaType;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:SendTotpDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 23:09
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendTotpDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private MfaType mfaType = MfaType.SMS;

    @NotNull
    private String mfaId;
}
