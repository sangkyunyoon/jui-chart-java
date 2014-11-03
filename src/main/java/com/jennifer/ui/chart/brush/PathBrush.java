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
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class PathBrush extends Brush {


    private Transform root;
    private int count;
    private Grid c;

    public PathBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public PathBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());
        count = chart.data().length();

        c = (Grid)options.get("c");
    }

    @Override
    public Object draw() {

        JSONArray target = options.array("target");

        for(int ti = 0, len = target.length(); ti < len; ti++) {
            String color = color(ti);

            Path path = root.path(opt()
                    .fill(color)
                    .fillOpacity(chart.theme("pathOpacity"))
                    .stroke(color)
                    .strokeWidth(chart.theme("pathBorderWidth"))
            );


            for (int i = 0; i < count; i++) {
                double value = chart.dataDouble(i, target.getString(ti));
                Option obj = c.get(i, value);

                if (i == 0) {
                    path.MoveTo(obj.x(), obj.y());
                } else {
                    path.LineTo(obj.x(), obj.y());
                }
            }

            path.Close();
        }

        return opt().put("root", root);
    }
}
