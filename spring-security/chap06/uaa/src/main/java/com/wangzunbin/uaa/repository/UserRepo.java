package com.wangzunbin.uaa.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.wangzunbin.uaa.domain.QUser;
import com.wangzunbin.uaa.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

/**
 * ClassName:UserRepo
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 10:09
 */
public interface UserRepo extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    Optional<User> findOptionalByUsername(String username);

    Optional<User> findOptionalByEmail(String email);

    long countByUsername(String username);

    long countByEmail(String email);

    long countByEmailAndUsernameIsNot(String  email, String username);

    long countByMobile(String mobile);

    long countByMobileAndUsernameIsNot(String mobile, String username);

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {
        bindings.bind(root.username).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.mobile).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.email).first(StringExpression::containsIgnoreCase);
    }
}