package com.wangzunbin.uaa.service.admin;

import com.wangzunbin.uaa.annotation.RoleAdminOrRead;
import com.wangzunbin.uaa.domain.Permission;
import com.wangzunbin.uaa.repository.PermissionRepo;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:PermissionAdminService
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 23:17
 */
@RequiredArgsConstructor
@Service
public class PermissionAdminService {

    private final PermissionRepo permissionRepo;

    @RoleAdminOrRead
    public List<Permission> findAll() {
        return permissionRepo.findAll();
    }
}
