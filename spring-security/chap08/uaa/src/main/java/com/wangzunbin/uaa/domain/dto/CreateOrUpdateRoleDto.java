package com.wangzunbin.uaa.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:CreateOrUpdateRoleDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 21:34
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    private String roleName;

    @NotNull
    @Size(max = 50)
    private String displayName;
}
