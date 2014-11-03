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
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class CircleGaugeBrush extends Brush {
    private Transform root;
    private int width;
    private int height;
    private double min;
    private double w;
    private double centerX;
    private double centerY;
    private double outerRadius;
    private double max;
    private double value;
    private double rate;

    public CircleGaugeBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }
    public CircleGaugeBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = (Transform)el("g").translate(chart.x(), chart.y()).put("class", "brush circle gauge");

        width = chart.width();
        height = chart.height();

        min = width;
        if(height < min) {
            min = height;
        }

        w = min / 2.0;
        centerX = width / 2.0;
        centerY = height / 2.0;
        outerRadius = w;


        min = options.optDouble("min", 0);
        max = options.optDouble("max", 100);
        value = options.optDouble("value", 0);
        rate = (value - min) / (max - min);


    }

    @Override
    public Object draw() {

        root.circle(opt()
                .cx(centerX)
                .cy(centerY)
                .r(outerRadius)
                .fill(chart.theme("gaugeBackgroundColor"))
                .stroke(color(0))
                .strokeWidth(2)
        );

        root.circle(opt()
                .cx(centerX)
                .cy(centerY)
                .r(outerRadius - rate)
                .fill(color(0))
        );

        return opt().put("root", root);
    }
}
