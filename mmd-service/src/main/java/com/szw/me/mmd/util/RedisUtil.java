package com.szw.me.mmd.util;

import java.util.StringJoiner;

public class RedisUtil {

    public static final String USER_PREFIX = "user";
    public static final String SPLITTER = "-";

    public static String wrapperUserKey(String code) {
        String originalKey = (code == null) ? "*" : code;
        return wrapperKey(USER_PREFIX, originalKey);
    }

    public static String wrapperKey(String prefix, String originalKey) {
        return new StringJoiner(SPLITTER).add(prefix).add(originalKey).toString();
    }
}
