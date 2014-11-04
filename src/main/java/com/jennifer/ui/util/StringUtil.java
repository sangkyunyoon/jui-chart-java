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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        return b.toString().trim();
    }

    public static String join(ArrayList<String> result, String splitter) {
        StringBuilder b = new StringBuilder();
        b.append(result.get(0));

        for(int i = 1, len = result.size(); i < len; i++) {
            b.append(splitter).append(result.get(i));
        }

        return b.toString().trim();
    }

    public static String createId() {
        return createId("jennifer");
    }

    public static String createId(String key) {
        return  key  + "-" + System.currentTimeMillis() + "-" + (Math.round(Math.random() * 100) % 100);
    }

    public static double parseDouble(String key, Option series) {

        // setting
        String[] names = JSONObject.getNames(series);
        for(String name : names) {
            if (series.object(name).has("max")) {
                key = key.replace("{" + name + "}", series.object(name).getDouble("max")+"");
            }
        }

        // caculate
        String[] list = key.split(" ");

        double value = Double.parseDouble(list[0]);

        for (int i = 1; i < list.length; i+=2) {
            String operator = list[i];

            if (operator.startsWith("+")) {
                value += Double.parseDouble(list[i+1]);
            } else if (operator.startsWith("/")) {
                value /= Double.parseDouble(list[i+1]);
            } else if (operator.startsWith("-")) {
                value -= Double.parseDouble(list[i+1]);
            } else if (operator.startsWith("*")) {
                value *= Double.parseDouble(list[i+1]);
            }
        }

        return value;
    }


}
