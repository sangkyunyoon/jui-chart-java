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


import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-27.
 */
public class StackColumnBrush extends Brush {

    private Transform root;
    private int maxY;
    private int outerPadding;
    private Grid x;
    private Grid y;
    private double zeroY;
    private int count;
    private JSONArray target;
    private double width;
    private double barWidth;

    public StackColumnBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }


    @Override
    public void drawBefore() {
        root = el("g", new JSONObject().put("class", "brush stack column")).translate(chart.area("x"), chart.area("y"));

        outerPadding = options.optInt("outerPadding", 0);

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        zeroY = y.get(0);
        count = chart.data().length();

        target = options.getJSONArray("target");

        width = x.rangeBand();
        barWidth  = width - outerPadding*2;

        if (barWidth < 0) {
            barWidth = width;
        }


    }

    @Override
    public Object draw() {

        for (int i = 0; i < count; i++) {
            Transform group = root.group();
            double value = 0;
            double startX = x.get(i) - barWidth / 2;
            double startY = y.get(0);

            for(int j = 0, jLen = target.length() ;j < jLen; j++) {
                double yValue = chart.dataDouble(i, target.getString(j)) + value;
                double endY = y.get(yValue);


                group.rect(new JSONObject()
                        .put("x", startX)
                        .put("y", startY > endY ? endY : startY)
                        .put("width", barWidth)
                        .put("height", Math.abs(startY - endY))
                        .put("fill",color(j))
                );

                startY = endY;
                value = yValue;
            }
        }

        return new JSONObject().put("root", root);
    }

}
