package com.wangzunbin.uaa.domain;

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

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
