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

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Orient;

import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-11-03.
 */
public class TitleWidget extends Widget {
    private Transform root;
    private String position;
    private String align;
    private String text;
    private double dx;
    private double dy;
    private double size;

    public TitleWidget(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g");

        position = options.optString("position", "top");
        align = options.optString("align", "center");
        text = options.optString("text", "");
        dx = options.optDouble("dx", 0);
        dy = options.optDouble("dy", 0);

        size = options.optDouble("size", 20);


    }

    @Override
    public Object draw() {

        if (!"".equals(text)) {
            drawTitle();
        }

        return new JSONObject().put("root", root);
    }

    private void drawTitle() {

        double x = 0;
        double y = 0;
        String anchor = "middle";

        if ("bottom".equals(position)) {
            y = chart.area("y2") + chart.padding("bottom") - size;
        } else {
            y = size;
        }

        if ("center".equals(align)) {
            x = chart.area("x") + chart.area("width")/2.0;
            anchor = "middle";
        } else if ("left".equals(align)) {
            x = chart.area("x");
            anchor = "start";
        } else {
            x = chart.area("x2");
            anchor = "end";
        }

        root.text(new JSONObject()
            .put("x", x + dx)
            .put("y", y + dy)
            .put("text-anchor",anchor)
            .put("font-family", chart.theme("fontFamily"))
            .put("font-size", chart.theme("titleFontSize"))
            .put("fill",chart.theme("titleFontColor"))
        ).textNode(text);
    }
}
