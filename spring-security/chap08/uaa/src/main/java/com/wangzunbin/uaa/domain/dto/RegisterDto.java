package com.wangzunbin.uaa.domain.dto;

import com.wangzunbin.uaa.annotation.ValidPassword;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.validator.PasswordMatches;
import com.wangzunbin.uaa.validator.ValidEmail;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * ClassName:RegisterDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 23:20
 */

@With
@Builder
@PasswordMatches
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

    @Pattern(regexp = Constants.PATTERN_MOBILE)
    @NotNull
    private String mobile;
}
