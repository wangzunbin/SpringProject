package com.wangzunbin.uaa.service.rolehierarchy;

import com.wangzunbin.uaa.repository.RoleRepo;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.val;

import static com.wangzunbin.uaa.util.Constants.ROLE_ADMIN;
import static com.wangzunbin.uaa.util.Constants.ROLE_STAFF;

/**
 * ClassName:RoleHierarchyService
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 21:16
 */

@RequiredArgsConstructor
@Service
public class RoleHierarchyService {
    private final RoleRepo roleRepo;

    public String getRoleHierarchyExpr() {
        val roles = roleRepo.findAll();
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream()
                        .map(permission -> role.getRoleName() + " > " + permission.getAuthority() + "\n"))
                .collect(Collectors.joining("", ROLE_ADMIN + " > " + ROLE_STAFF, "\n"));
    }

}
