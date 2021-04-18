package com.wangzunbin.uaa.annotation;

import com.wangzunbin.uaa.util.Constants;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName:RoleAdminNotSelf
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/14 22:36
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('" +
        Constants.AUTHORITY_ADMIN +
        "') and authentication.name != #username")
public @interface RoleAdminNotSelf {
}
