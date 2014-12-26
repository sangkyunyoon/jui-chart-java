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

import java.io.*;
import java.net.URL;
import java.util.Enumeration;

import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-28.
 */
public class JSONUtil {

    private static final java.lang.String JSONPATH_REGEX = "\\.";

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

        if(obj == null) return null;
        Option o = new Option();

        String[] names = JSONObject.getNames(obj);

        if (names == null) return null;

        for(String name : names) {
            o.put(name, obj.get(name));
        }

        return o;
    }

    public static Option clone(Option obj) {
        return clone((JSONObject) obj);
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

        return new Option(readFile("com/jennifer/ui/" + filename));
    }

    public static String readFile(String filename)
    {
        String content = null;

        InputStream url  = JSONUtil.class.getResourceAsStream("/" + filename);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while( (line = reader.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();
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


    private static Object getPathObject(String key, Object opt) {

        if (opt instanceof JSONObject) {
            return ((JSONObject)opt).opt(key);
        } else if (opt instanceof JSONArray) {
            return ((JSONArray)opt).opt(Integer.parseInt(key));
        } else {
            return null;
        }
    }

    public static boolean set(JSONObject options, String jsonPath, Object value)  {
        String[] path = jsonPath.split(JSONPATH_REGEX);

        // path 형태로 설정한다.
        // chart.set("grid.y.unit", new Object);  하면 실제로 grid.y.unit 속성에 object 객체를 설정한다.

        Object start = options;
        int i = 0;
        for(; i < path.length - 1; i++) {
            Object o =  getPathObject(path[i], start);

            if (o != null) {
                start = o;
            } else  {
                return false;
            }
        }

        if (start instanceof JSONObject) {
            ((JSONObject)start).put(path[i], value);
            return true;
        } else if (start instanceof JSONArray) {
            ((JSONArray)start).put(Integer.parseInt(path[i]), value);
            return true;
        }

        return false;
    }

    public static  Object get(JSONObject options, String jsonPath) {
        String[] path = jsonPath.split(JSONPATH_REGEX);

        Object start = options;
        int i = 0;
        for(; i < path.length - 1; i++) {
            Object o =  getPathObject(path[i], start);

            if (o != null) {
                start = o;
            } else {
                break;
            }
        }

        if (start instanceof JSONObject) {
            return ((JSONObject)start).get(path[i]);
        } else if (start instanceof JSONArray) {
            return ((JSONArray)start).put(Integer.parseInt(path[i]));
        } else if (start != null) {
            return start;
        }

        return null;
    }

}
