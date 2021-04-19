package com.wangzunbin.uaa.domain.dto;

import com.wangzunbin.uaa.domain.Permission;
import com.wangzunbin.uaa.domain.Role;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:RoleDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 23:16
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String roleName;

    private String displayName;

    private boolean builtIn;

    private Set<Permission> permissions;

    public static Function<Role, RoleDto> fromRole = (role) -> RoleDto.builder()
            .id(role.getId())
            .roleName(role.getRoleName())
            .displayName(role.getDisplayName())
            .builtIn(role.isBuiltIn())
            .permissions(role.getPermissions())
            .build();
}
