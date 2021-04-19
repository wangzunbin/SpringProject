package com.wangzunbin.uaa.service.validation;

import com.wangzunbin.uaa.repository.RoleRepo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:RoleValidationService
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 21:33
 */

@RequiredArgsConstructor
@Service
public class RoleValidationService {

    private final RoleRepo roleRepo;

    public boolean isRoleNameExisted(String roleName) {
        return roleRepo.countByRoleNameIgnoreCase(roleName) > 0;
    }

    public boolean isRoleNameExistedAndIdIsNot(String roleName, Long id) {
        return roleRepo.countByRoleNameIgnoreCaseAndIdNot(roleName, id) > 0;
    }

    public boolean isRoleAssigned(Long id) {
        return roleRepo.countByAssigned(id) > 0;
    }
}
