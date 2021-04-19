package com.wangzunbin.uaa.rest;

import com.querydsl.core.types.Predicate;
import com.wangzunbin.uaa.domain.Permission;
import com.wangzunbin.uaa.domain.Role;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.domain.dto.CreateOrUpdateRoleDto;
import com.wangzunbin.uaa.domain.dto.CreateUserDto;
import com.wangzunbin.uaa.domain.dto.RoleDto;
import com.wangzunbin.uaa.domain.dto.UserDto;
import com.wangzunbin.uaa.domain.dto.UserProfileDto;
import com.wangzunbin.uaa.service.UserService;
import com.wangzunbin.uaa.service.admin.PermissionAdminService;
import com.wangzunbin.uaa.service.admin.RoleAdminService;
import com.wangzunbin.uaa.service.admin.UserAdminService;
import com.wangzunbin.uaa.service.validation.RoleValidationService;
import com.wangzunbin.uaa.service.validation.UserValidationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * ClassName:AdminResource
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 23:15
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminResource {


    private final UserService userService;
    private final UserAdminService userAdminService;
    private final RoleAdminService roleAdminService;
    private final PermissionAdminService permissionAdminService;
    private final UserValidationService userValidationService;
    private final RoleValidationService roleValidationService;

    @GetMapping("/users")
    public Page<UserDto> findAllUsers(@QuerydslPredicate(root = User.class) Predicate predicate, Pageable pageable) {
        return userAdminService.findAll(predicate, pageable)
                .map(user -> UserDto.fromUser.apply(user));
    }

    @GetMapping("/users/{username}")
    public UserDto findByUsername(@PathVariable String username) {
        return userAdminService.findByUsername(username)
                .map(user -> UserDto.fromUser.apply(user))
                .orElseThrow();
    }

    @PostMapping("/users")
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        userValidationService.validateUserUniqueFields(createUserDto.getUsername(), createUserDto.getEmail(), createUserDto.getMobile());
        val user = userAdminService.createUser(createUserDto);
        return UserDto.fromUser.apply(user);
    }

    @PutMapping("/users/{username}")
    public UserDto updateUser(@PathVariable String username, @Valid @RequestBody UserProfileDto userProfileDto) {
        return userService.findOptionalByUsername(username)
                .map(user -> {
                    val toSave = user
                            .withName(userProfileDto.getName())
                            .withEmail(userProfileDto.getEmail())
                            .withMobile(userProfileDto.getMobile());
                    val saved = userService.saveUser(toSave);
                    return UserDto.fromUser.apply(saved);
                })
                .orElseThrow();
    }

    @PutMapping("/users/{username}/enabled")
    public UserDto toggleUserEnabled(@PathVariable String username) {
        val user = userAdminService.toggleEnabled(username);
        return UserDto.fromUser.apply(user);
    }

    @GetMapping("/users/{username}/roles/available")
    public Set<Role> getUserAvailableRoles(@PathVariable String username) {
        return userAdminService.findAvailableRolesByUserId(username);
    }

    @PutMapping("/users/{username}/roles")
    public UserDto updateUserRoles(@PathVariable String username, @RequestBody List<Long> roleIds) {
        val user = userAdminService.updateRoles(username, roleIds);
        return UserDto.fromUser.apply(user);
    }

    @GetMapping("/roles")
    public Page<RoleDto> findAllRoles(@QuerydslPredicate(root = Role.class) Predicate predicate, Pageable pageable) {
        return roleAdminService.findAll(predicate, pageable).map(role -> RoleDto.fromRole.apply(role));
    }

    @PostMapping("/roles")
    public RoleDto addRole(@Valid @RequestBody CreateOrUpdateRoleDto createOrUpdateRoleDto) {
        val role = roleAdminService.createRole(createOrUpdateRoleDto);
        return RoleDto.fromRole.apply(role);
    }

    @GetMapping("/roles/{roleId}")
    public RoleDto getRole(@PathVariable Long roleId) {
        return roleAdminService.findById(roleId)
                .map(role -> RoleDto.fromRole.apply(role))
                .orElseThrow();
    }

    @PutMapping("/roles/{roleId}")
    public RoleDto updateRole(@PathVariable Long roleId, @Valid @RequestBody CreateOrUpdateRoleDto createOrUpdateRoleDto) {
        val role = roleAdminService.updateRole(roleId, createOrUpdateRoleDto);
        return RoleDto.fromRole.apply(role);
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRole(@PathVariable Long roleId) {
        roleAdminService.deleteRole(roleId);
    }

    @GetMapping("/roles/{roleId}/permissions/available")
    public Set<Permission> findAvailablePermissions(@PathVariable Long roleId) {
        return roleAdminService.findAvailablePermissions(roleId);
    }

    @PutMapping("/roles/{roleId}/permissions")
    public RoleDto removeRolePermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        val role = roleAdminService.updatePermissions(roleId, permissionIds);
        return RoleDto.fromRole.apply(role);
    }

    @GetMapping("/permissions")
    public List<Permission> findAllPermissions() {
        return permissionAdminService.findAll();
    }

    @GetMapping("/validation/email")
    public boolean validateEmail(@RequestParam String email, @RequestParam String username) {
        return userValidationService.isEmailExistedAndUsernameIsNot(email, username);
    }

    @GetMapping("/validation/mobile")
    public boolean validateMobile(@RequestParam String mobile, @RequestParam String username) {
        return userValidationService.isMobileExistedAndUsernameIsNot(mobile, username);
    }

    @GetMapping("/validation/role-name")
    public boolean validateRoleName(@RequestParam String roleName) {
        return roleValidationService.isRoleNameExisted(roleName);
    }

    @GetMapping("/validation/roles/{id}/role-name")
    public boolean validateRoleNameNotSelf(@PathVariable Long id, @RequestParam String roleName) {
        return roleValidationService.isRoleNameExistedAndIdIsNot(roleName, id);
    }
}
