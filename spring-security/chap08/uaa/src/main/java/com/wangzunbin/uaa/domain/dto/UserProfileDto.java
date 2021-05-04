package com.wangzunbin.uaa.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.validator.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * ClassName:UserProfileDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 23:19
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Pattern(regexp = Constants.PATTERN_MOBILE)
    @NotNull
    private String mobile;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

}
