package com.jennifer.ui.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-28.
 */
public class JSONUtil {

    public static JSONObject extend(JSONObject o, JSONObject attr) {
        JSONObject newObj = new JSONObject();

        JSONArray names1 = o.names();
        if (names1 != null) {
            for(int i = 0, len = names1.length(); i < len; i++) {
                String key = names1.getString(i);
                Object value = o.get(key);

                newObj.put(key, value);
            }
        }


        JSONArray names = attr.names();

        for(int i = 0, len = names.length(); i < len; i++) {
            String key = names.getString(i);
            Object value = attr.get(key);

            newObj.put(key, value);
        }

        return newObj;
    }

    public static String join(JSONArray list, String seperator) {
        StringBuilder sb = new StringBuilder();

        if (list != null) {
            for(int i  = 0, len = list.length() ; i < len; i++) {
                if (i == 0) {
                    sb.append(list.getString(i));
                } else {
                    sb.append(seperator).append(list.getString(i));
                }
            }
        }

        return sb.toString();
    }

    public static JSONObject clone(JSONObject jsonObject) {
        return extend(new JSONObject(), jsonObject);
    }

    public static JSONArray toJSONArray(String str[]) {
        JSONArray o = new JSONArray();
        for(int i = 0, len = str.length; i < len; i++) {
            o.put(str[i]);
        }
        return o;
    }
}
