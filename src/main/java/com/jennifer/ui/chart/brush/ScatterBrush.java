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


import com.jennifer.ui.util.dom.Polygon;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-27.
 */
public class ScatterBrush extends Brush {

    private Transform root;
    private String symbol;
    private int size;
    private JSONObject target;

    public ScatterBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.area("x"), chart.area("y"));

        symbol = options.optString("symbol", "circle");
        size = options.optInt("size", 7);
    }

    @Override
    public Object draw() {
        return drawScatter(getXY());
    }

    protected Object drawScatter(JSONArray points) {
        for(int i = 0; i < points.length(); i++) {
            JSONObject point =  points.getJSONObject( i);
            for(int j = 0; j < point.getJSONArray("x").length(); j++) {
                Transform p = this.createScatter(new JSONObject()
                        .put("x", point.getJSONArray("x").getDouble(j))
                        .put("y", point.getJSONArray("y").getDouble(j))
                        , i);
                root.append(p);
            }
        }

        return new JSONObject().put("root", root);
    }

    private Transform createScatter(JSONObject pos, int i) {

        target = chart.series(options.getJSONArray("target").getString(i));
        String s = (!target.has("symbol")) ? symbol : target.getString("symbol");
        double h = size ;
        double w = h ;

        String color = color(i);
        String borderColor = chart.theme("scatterBorderColor");
        double borderWidth = chart.themeDouble("scatterBorderWidth");

        Transform  g = null;

        if("triangle".equals(s) || "cross".equals(s)) {
            g = el("g", new JSONObject().put("width", w).put("height", h)).translate(pos.getDouble("x") - (w / 2), pos.getDouble("y") - (h / 2));

            if ("triangle".equals(s)) {
                Polygon poly = g.polygon();

                poly.point(0, h)
                        .point(w, h)
                        .point(w / 2, 0);
            } else {
                g.line(new JSONObject().put("stroke", color).put("stroke-width", borderWidth * 2).put("x1", 0).put("y1", 0).put("x2", w).put("y2", h));
                g.line(new JSONObject().put("stroke", color).put("stroke-width", borderWidth * 2).put("x1", 0).put("y1", w).put("x2", h).put("y2", 0));
            }
        } else  {
            if("rectangle".equals(s)) {
                g = el("rect", new JSONObject().put("width", w).put("height", h).put("x", pos.getDouble("x") - (w / 2)).put("y", pos.getDouble("y") - (h / 2)));
            } else {
                g = el("ellipse", new JSONObject().put("rx", w / 2).put("ry", h/2).put("cx", pos.getDouble("x")).put("cy", pos.getDouble("y")));
            }
        }

        if (!"cross".equals(s)) {
            g.attr(new JSONObject().put("fill", color).put("stroke", borderColor).put("stroke-width", borderWidth));
        }

        return g;
    }
}
