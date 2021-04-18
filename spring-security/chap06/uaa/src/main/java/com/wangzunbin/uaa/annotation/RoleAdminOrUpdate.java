package com.wangzunbin.uaa.annotation;

import com.wangzunbin.uaa.util.Constants;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName:RoleAdminOrUpdate
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/14 22:35
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('" + Constants.ROLE_ADMIN + "', '" + Constants.AUTHORITY_USER_UPDATE + "')")
public @interface RoleAdminOrUpdate {

}
