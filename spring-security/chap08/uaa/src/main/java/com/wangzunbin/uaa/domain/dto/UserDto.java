package com.wangzunbin.uaa.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangzunbin.uaa.annotation.ValidPassword;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.validator.PasswordMatches;
import com.wangzunbin.uaa.validator.ValidEmail;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@JsonIgnoreProperties(ignoreUnknown = true)
@With
@Builder
@PasswordMatches
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
