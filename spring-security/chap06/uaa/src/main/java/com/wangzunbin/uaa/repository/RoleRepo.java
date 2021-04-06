package com.wangzunbin.uaa.repository;

import com.wangzunbin.uaa.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * ClassName:RoleRepo
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 10:11
 */

public interface RoleRepo  extends JpaRepository<Role, Long> {

    Set<Role> findByAuthorityIn(Set<String> authority);

    Optional<Role> findOptionalByAuthority(String authority);
}
