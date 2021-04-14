package com.wangzunbin.uaa.util;

public class Constants {
    public static final String PROBLEM_BASE_URI = "https://wzb.com";

    // ---- 正则表达式相关 ----
    public static final String PATTERN_MOBILE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    public static final String PATTERN_ROLE_NAME = "^[a-zA-Z0-9_]{3,50}$";
    public static final String PATTERN_USERNAME = "^[a-z0-9_-]{3,50}$";

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String CACHE_MFA = "cacheMfa";
}
