package com.wangzunbin.uaa.domain;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * ClassName:User  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:27   <br/>
 */

@Data
public class UserDto implements Serializable {

    @NotBlank
    @Size(min = 4, max = 50, message = "用户畅读必须在4到50个字符之间")
    private String username;
    private String password;
    private String matchPassword;
    @Email
    private String email;
    private String name;
}
