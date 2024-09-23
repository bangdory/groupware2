package com.groupware.erp.util;

public class StringUtil {

    // string이 null이거나 비었으면 true
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    // string이 null이거나 비었으면 기본값을 출력
    public static String nvl(String str, String defaultValue) {
        return str == null || str.length() == 0 ? defaultValue : str;
    }
}
