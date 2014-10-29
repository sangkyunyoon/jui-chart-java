package com.jennifer.ui.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);
                Object value = attr.get(key);

                newObj.put(key, value);
            }
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
        return new JSONObject(jsonObject.toString());
    }

    public static JSONArray toJSONArray(String str[]) {
        JSONArray o = new JSONArray();
        for(int i = 0, len = str.length; i < len; i++) {
            o.put(str[i]);
        }
        return o;
    }

    public static JSONObject loadJSONFile(String filename) {
        return new JSONObject(readFile("src/main/java/com/jennifer/ui/" + filename));
    }

    public static String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void reverse(JSONArray domain) {
        JSONArray temp = new JSONArray();
        for(int i = 0, len = domain.length(); i < len; i++) {
            temp.put(i, domain.get(i));
        }

        for(int i = temp.length() -1, j = 0; i >= 0; i--, j++) {
            domain.put(j, temp.get(i));
        }
    }
}
