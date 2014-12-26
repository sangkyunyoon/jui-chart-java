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
public class FullStackBrush extends Brush {

    private Transform root;
    private int outerPadding;
    private Grid x;
    private Grid y;
    private double zeroY;
    private int count;
    private double width;
    private double barWidth;
    private boolean hasText;

    public FullStackBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {

        root = el("g").translate(chart.area("x"), chart.area("y"));

        outerPadding = options.optInt("outerPadding", 15);

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        zeroY = y.get(0);
        count = chart.data().length();

        width = x.rangeBand();
        barWidth = width - outerPadding * 2;

        hasText = options.optBoolean("text", false);

    }

    @Override
    public Object draw() {
        int chart_height = chart.area("height");
        JSONArray target = options.getJSONArray( "target");

        for (int i = 0; i < count; i++) {
            double startX = x.get(i) - barWidth / 2;
            double sum = 0;
            JSONArray list = new JSONArray();

            for (int j = 0, jLen = target.length(); j < jLen; j++) {
                double height = chart.dataDouble(i, target.getString(j));

                sum += height;
                list.put(height);
            }

            double startY = 0, max = y.max(), current = max;

            for (int j = list.length() - 1; j >= 0; j--) {
                double height = chart_height - y.rate(list.getDouble(j) , sum);

                root.rect(new JSONObject()
                        .put("x", startX)
                        .put("y", startY)
                        .put("width", barWidth)
                        .put("height", height)
                        .put("fill",color(j))
                );

                if (hasText) {
                    double percent = Math.round((list.getDouble(j)/sum)*max);
                    root.text(new JSONObject().put("x", startX + barWidth/2).put("y", startY + height/2 + 8).put("text-anchor","middle")).textNode(((current - percent < 0 ) ? current+"" : percent) + "%");

                    current -= percent;
                }

                startY += height;
            }
        }

        return new JSONObject().put("root", root);
    }
}
