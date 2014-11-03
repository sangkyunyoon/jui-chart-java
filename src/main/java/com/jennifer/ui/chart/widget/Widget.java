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

package com.jennifer.ui.chart.widget;

import com.jennifer.ui.chart.AbstractDraw;
import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Orient;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.Option;
import org.json.JSONObject;

public abstract class Widget extends AbstractDraw {

    protected ChartBuilder chart;
    protected Option options;

    public Widget(ChartBuilder chart, Option options) {
        this.chart = chart;
        this.options = options;

        init();
    }

    public Widget(ChartBuilder chart, JSONObject options) {
        this(chart, JSONUtil.clone(options));
    }

    public void init() {

    }

    @Override
    public void drawBefore() {

    }

    @Override
    public Object draw() {
        return null;
    }

    public static JSONObject create(String type) { return new JSONObject().put("type", type); }
    public static JSONObject title() { return create("title"); }
    public static JSONObject legend() { return create("legend"); }
}
