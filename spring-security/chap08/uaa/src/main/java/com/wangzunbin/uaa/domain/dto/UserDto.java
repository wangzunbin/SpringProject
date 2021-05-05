package com.wangzunbin.uaa.domain.dto;

import com.wangzunbin.uaa.domain.User;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {


    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String email;

    private String mobile;

    private boolean enabled;

    private boolean usingMfa;

    private Set<RoleDto> roles;

    public static Function<User, UserDto> fromUser = (user) -> UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .name(user.getName())
            .email(user.getEmail())
            .mobile(user.getMobile())
            .enabled(user.isEnabled())
            .usingMfa(user.isUsingMfa())
            .roles(user.getRoles().stream()
                    .map(role -> RoleDto.builder()
                            .id(role.getId())
                            .roleName(role.getRoleName())
                            .displayName(role.getDisplayName())
                            .builtIn(role.isBuiltIn())
                            .build())
                    .collect(Collectors.toSet()))
            .build();
}
