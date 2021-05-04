package com.wangzunbin.uaa.annotation;

import com.wangzunbin.uaa.util.Constants;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName:RoleAdmin
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 21:29
 */

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('" +
        Constants.AUTHORITY_ADMIN +
        "')")
public @interface RoleAdmin {
}
