package com.wangzunbin.uaa.security.rolehierarchy;

import com.wangzunbin.uaa.repository.RoleRepo;

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
 * @version 1.0 2021/5/5 21:54
 */

@RequiredArgsConstructor
@Service
public class RoleHierarchyService {

 private final RoleRepo roleRepo;

 public String getRoleHierarchyExpr() {
  val roles = roleRepo.findAll();
  return roles.stream()
          .flatMap(role -> role.getPermissions().stream()
                  .map(permission -> role.getRoleName() + " > " + permission.getAuthority() + " "))
          .collect(Collectors.joining(" ", ROLE_ADMIN + " > " + ROLE_STAFF + " ", ""));
 }
}