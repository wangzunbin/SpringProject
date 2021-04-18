package com.wangzunbin.uaa.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.wangzunbin.uaa.domain.QRole;
import com.wangzunbin.uaa.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;
import java.util.Set;

/**
 * ClassName:RoleRepo
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 10:11
 */

public interface RoleRepo  extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role>, QuerydslBinderCustomizer<QRole> {

    Set<Role> findByAuthorityIn(Set<String> authority);

    Optional<Role> findOptionalByAuthority(String authority);

    Optional<Role> findOptionalByRoleName(String roleName);

    Set<Role> findByIdIn(Set<Long> ids);

    @Override
    default void customize(QuerydslBindings bindings, QRole root) {
        bindings.bind(root.roleName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.displayName).first(StringExpression::containsIgnoreCase);
    }
}
