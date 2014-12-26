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
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class ColumnBrush extends Brush {
    private Transform root;
    private int outerPadding;
    private int innerPadding;
    private Grid x;
    private Grid y;
    private double zeroY;
    private int count;
    private double width;
    private double half_width;
    private JSONArray target;
    private double columnWidth;


    public ColumnBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }
    public ColumnBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }


    @Override
    public void drawBefore() {

        root = el("g").translate(chart.x(), chart.y());

        outerPadding = options.optInt("outerPadding", 2);
        innerPadding = options.optInt("innerPadding", 1);

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        zeroY = y.get(0);
        count = chart.data().length();

        target = options.target();

        width = x.rangeBand();
        half_width = width - outerPadding*2;
        columnWidth = (half_width - (target.length()-1) * innerPadding) / target.length();

    }

    @Override
    public Object draw() {

        for(int i = 0; i < count; i++) {
            double startX = x.get(i) - half_width/2;

            Transform group = root.group();

            for(int j = 0, len = target.length(); j < len; j++) {

                double startY = y.get(chart.dataDouble(i, target.getString(j)));
                double h = Math.abs(zeroY - startY);

                Option o = new Option().x(startX).height(h).width(columnWidth).fill(this.color(j));
                if (startY <= zeroY) {
                    o.y(startY);
                } else {
                    o.y(zeroY);
                }

                group.rect(o);

                startX = startX + (columnWidth + innerPadding);

            }

        }

        return opt().put("root", root);
    }
}
