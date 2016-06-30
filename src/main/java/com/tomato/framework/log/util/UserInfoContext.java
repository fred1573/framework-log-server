package com.tomato.framework.log.util;


import org.apache.commons.lang.StringUtils;

public class UserInfoContext {

    private static final String NO_CURRENT_USERID = StringUtils.EMPTY;
    private static String CURRENT_USER = null;

    /**
     * 得到当前用户名
     */
    public static String currentUserID() {
        return CURRENT_USER == null ? NO_CURRENT_USERID : CURRENT_USER;
    }

    /**
     *
     */
    public static void setUserInfo(String userId) {
        CURRENT_USER = userId;
    }

    /**
     * 在Filter过滤器完成时，将userName和userType从ThreadLocal中清除
     */
    public static void release() {
        CURRENT_USER = null;
    }
}

