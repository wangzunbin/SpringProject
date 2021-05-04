package com.wangzunbin.uaa.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.validator.ValidEmail;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:CreateUserDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/14 22:41
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Pattern(regexp = Constants.PATTERN_USERNAME)
    private String username;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

    @Pattern(regexp = Constants.PATTERN_MOBILE)
    @NotNull
    private String mobile;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Builder.Default
    @NotNull
    private boolean usingMfa = true;
}
