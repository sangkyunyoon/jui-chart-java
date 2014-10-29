package com.jennifer.ui.util;

import java.util.Date;

/**
 * Created by Jayden on 2014-10-24.
 */
public class StringUtil {
    public static String join(String str[], String splitter) {
        StringBuilder b = new StringBuilder();
        b.append(str[0]);

        for(int i = 1, len = str.length; i < len; i++) {
            b.append(splitter).append(str[i]);
        }

        return b.toString();
    }

    public static String createId() {
        return createId("jennifer");
    }

    public static String createId(String key) {
        return  key  + "-" + System.currentTimeMillis() + "-" + (Math.round(Math.random() * 100) % 100);
    }
}
