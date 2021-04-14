package com.wangzunbin.uaa.annotation;

import com.wangzunbin.uaa.util.Constants;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName:RoleAdminOrSelfWithUserParam
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/14 22:28
 */

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("authentication.name == #user.username or " +
        "hasAnyAuthority('" + Constants.ROLE_ADMIN + "', '" + Constants.AUTHORITY_USER_UPDATE + "')")
public @interface RoleAdminOrSelfWithUserParam {

}
