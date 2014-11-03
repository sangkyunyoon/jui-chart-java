/*
 * Copyright (C) 2014 (JenniferSoft Inc.)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jennifer.ui.util;

import org.json.JSONArray;
import org.json.JSONObject;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-28.
 */
public class JSONUtil {

    public static Option extend(Option o, Option attr) {
        Option newObj = opt();

        JSONArray names1 = o.names();
        if (names1 != null) {
            for(int i = 0, len = names1.length(); i < len; i++) {
                String key = names1.getString(i);
                Object value = o.get(key);

                newObj.put(key, value);
            }
        }


        JSONArray names =  attr.names();
        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);
                Object value = attr.get(key);

                newObj.put(key, value);
            }
        }

        return newObj;
    }


    public static Option extend(JSONObject options) {
        Option newObj = new Option();

        JSONArray names =  options.names();
        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);
                Object value = options.get(key);

                newObj.put(key, value);
            }
        }

        return newObj;
    }

    public static String join(OptionArray list, String seperator) {
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

    public static Option clone(JSONObject obj) {
        return new Option(obj.toString());
    }

    public static Option clone(Option obj) {
        return new Option(obj.toString());
    }

    public static OptionArray clone(JSONArray array) {
        if (array == null) return new OptionArray();
        return new OptionArray(array.toString());
    }

    public static OptionArray toOptionArray(String str[]) {
        OptionArray o = new OptionArray();
        for(int i = 0, len = str.length; i < len; i++) {
            o.put(str[i]);
        }
        return o;
    }

    public static Option loadJSONFile(String filename) {
        return new Option(readFile("src/main/java/com/jennifer/ui/" + filename));
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

    public static void reverse(OptionArray domain) {
        OptionArray temp = new OptionArray();
        for(int i = 0, len = domain.length(); i < len; i++) {
            temp.put(i, domain.get(i));
        }

        for(int i = temp.length() -1, j = 0; i >= 0; i--, j++) {
            domain.put(j, temp.get(i));
        }
    }

    public static String format(String s, Option row) {
        // TODO: implements json format string
        return s;
    }

}
