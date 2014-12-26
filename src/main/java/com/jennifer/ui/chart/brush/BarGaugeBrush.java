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

package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;

import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;

/**
 * Created by Jayden on 2014-10-27.
 */
public class BarGaugeBrush extends Brush {
    private Transform root;
    private int max;
    private int x;
    private int y;
    private int count;
    private JSONArray target;
    private int cut;
    private int size;
    private String align;
    private boolean split;

    public BarGaugeBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }


    @Override
    public void drawBefore() {

        root = (Transform)el("g").translate(chart.area("x"), chart.area("y")).put("class", "brush bar gauge");

        max = chart.area("width");

        x = 0;
        y = 0;

        count = chart.data().length();

        size = options.optInt("size", 20);
        cut = options.optInt("cut", 5);

        align = options.optString("align", "left");
        split = options.optBoolean("split", false);

    }

    @Override
    public Object draw() {

        for(int i = 0; i < count; i++) {

            JSONObject data = chart.data(i);

            Transform group = root.group();

            String color = color(i);

            JSONObject o = new JSONObject()
                    .put("x", x)
                    .put("y", y + size/2 + cut)
                    .put("text-anchor","end")
                    .put("fill",color);

            String text = "";
            if (data.has(options.getString("title"))) {
                text = data.getString(options.getString("title"));
            } else {
                text = data.getString("title");
            }

            group.append(chart.text(o, text));

            JSONObject rectOpt = new JSONObject()
                    .put("x", x + cut)
                    .put("y", y)
                    .put("width", max)
                    .put("height", size)
                    .put("fill",chart.theme("gaugeBackgroundColor"));
            group.rect(rectOpt);

            double value = data.getDouble("value") * max / 100;
            double ex = (100 - data.getDouble("value")) * max / 100;
            double startX = x + cut;

            if ("center".equals(align)) {
                startX += (max/2 - value/2);
            } else if ("right".equals(align)) {
                startX += max - value;
            }

            JSONObject rectOpt2 = new JSONObject()
                    .put("x", startX)
                    .put("y", y)
                    .put("width", value)
                    .put("height", size)
                    .put("fill",color);

            group.rect(rectOpt2);

            double textX = 0;
            String textAlign = "";
            String textColor = color;

            if (split) {
                textX = x + value + cut * 2 + ex;
                textAlign = "start";
                textColor = color;
            } else {
                textX = x + cut * 2;
                textAlign = "start";
                textColor = "white";

                if ("center".equals(align)) {
                    textX = x + cut + max / 2;
                    textAlign = "middle";
                } else if ("right".equals(align)) {
                    textX = x + max;
                    textAlign = "end";
                }
            }

            JSONObject textOpt = new JSONObject()
                    .put("x", textX)
                    .put("y", y + size/2 + cut)
                    .put("text-anchor",textAlign)
                    .put("fill",textColor);

            group.append(chart.text(textOpt, data.getDouble("value") + "%"));

            y += size + cut;
        }

        return new JSONObject().put("root", root);
    }
}
