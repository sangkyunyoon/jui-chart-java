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

package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.DomUtil;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import org.json.JSONArray;

/**
 * Created by Jayden on 2014-10-27.
 */
public class Transform extends DomUtil {

    private Option orders = new Option();

    public Transform(String tagName, Option attr) {
        super(tagName, attr);
    }

    public Transform(String tagName) {
        super(tagName);
    }

    public Transform translate(double x) {
        orders.put("translate", x);
        return this;
    }

    public Transform rotate(double angle) {
        orders.put("rotate", angle);
        return this;
    }

    public Transform translate(double x, double y) {
        orders.put("translate", x + " " + y);
        return this;
    }

    public Transform rotate(double angle, double x, double y) {
        orders.put("rotate", angle + " " + x + " " + y);
        return this;
    }

    public String render() {
        return render(0, 4);
    }

    public String render(int tabIndex, int tabSize) {

        OptionArray list = new OptionArray();
        JSONArray names = orders.names();

        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);

                if (orders.has(key)) {
                    list.put(key + "(" + orders.string(key) + ")");
                }
            }

            put("transform", JSONUtil.join(list, " "));

        }

        return super.render(tabIndex, tabSize);
    }

}
