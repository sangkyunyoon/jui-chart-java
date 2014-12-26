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


import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Polygon;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-27.
 */
public class ScatterPathBrush extends Brush {


    private Transform root;
    private String symbol;
    private double size;

    public ScatterPathBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.area("x"), chart.area("y"));

        symbol = options.optString("symbol", "circle");
        size = options.optDouble("size", 7);
    }

    @Override
    public Object draw() {

        drawScatter(getXY());

        return new JSONObject().put("root", root);
    }

    private void drawScatter(JSONArray points) {
        double height = size;
        double width = height;

        Path path = root.path(new JSONObject()
                .put("fill",color(0))
                .put("stroke", color(0))
                .put("stroke-width",chart.theme("scatterBorderWidth")));

        JSONArray t = options.getJSONArray( "target");

        for(int i = 0; i < points.length(); i++) {
            JSONObject target = chart.series(t.getString(i));
            String s = (!target.has("symbol")) ? symbol : target.getString("symbol");

            JSONObject point =  points.getJSONObject( i);
            for(int j = 0; j < point.getJSONArray( "x").length(); j++) {

                if ("circle".equals(s)) {
                    path.circle(point.getJSONArray( "x").getDouble(j), point.getJSONArray( "y").getDouble(j), width/2);
                } else if ("rect".equals(s) || "rectangle".equals(s)) {
                    path.rect(point.getJSONArray( "x").getDouble(j), point.getJSONArray( "y").getDouble(j), width, height);
                } else if ("triangle".equals(s)) {
                    path.triangle(point.getJSONArray( "x").getDouble(j), point.getJSONArray( "y").getDouble(j), width, height);
                } else if ("cross".equals(s)) {
                    path.cross(point.getJSONArray( "x").getDouble(j), point.getJSONArray( "y").getDouble(j), width, height);
                }


            }
        }
    }
}
