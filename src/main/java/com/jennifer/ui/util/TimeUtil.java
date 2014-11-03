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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Date get(long d) {
        Date newDate = new Date();
        newDate.setTime(d);

        return newDate;
    }

    public static long add(long d, String type, int interval) {
        return add(get(d), type, interval).getTime();
    }

    public static Date add(Date d, String type, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        if (Time.YEARS.equals(type)) {
            c.add(Calendar.YEAR, interval);
        } else if (Time.MONTHS.equals(type)) {
            c.add(Calendar.MONTH, interval);
        } else if (Time.DAYS.equals(type)) {
            c.add(Calendar.DATE, interval);
        } else if (Time.HOURS.equals(type)) {
            c.add(Calendar.HOUR_OF_DAY, interval);
        } else if (Time.MINUTES.equals(type)) {
            c.add(Calendar.MINUTE, interval);
        } else if (Time.SECONDS.equals(type)) {
            c.add(Calendar.SECOND, interval);
        } else if (Time.MILLISECONDS.equals(type)) {
            c.add(Calendar.MILLISECOND, interval);
        } else if (Time.WEEKS.equals(type)) {
            c.add(Calendar.DATE, interval * 7);
        }

        return c.getTime();
    }

    public static String format(long d, String format) {
        return format(get(d), format);
    }

    public static String format(Date d, String format) {
        return new SimpleDateFormat(format).format(d);
    }

    public static String format(long d, JSONArray step) {

        String type = step.getString(0);
        String format = "";

        if (Time.YEARS.equals(type)) {
            format = "yyyy";
        } else if (Time.MONTHS.equals(type)) {
            format = "yyyy-MM";
        } else if (Time.DAYS.equals(type)) {
            format = "MM-dd";
        } else if (Time.HOURS.equals(type)) {
            format = "HH";
        } else if (Time.MINUTES.equals(type)) {
            format = "HH:mm";
        } else if (Time.SECONDS.equals(type)) {
            format = "HH:mm:ss";
        } else if (Time.MILLISECONDS.equals(type)) {
            format = "ss";
        }


        return format(get(d), format);
    }
}
