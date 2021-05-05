package com.wangzunbin.uaa.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:LoginDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 0:10
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
