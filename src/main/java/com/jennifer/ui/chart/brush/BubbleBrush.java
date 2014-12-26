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

import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;

/**
 * Created by Jayden on 2014-10-27.
 */
public class BubbleBrush extends Brush {
    private Transform root;
    private int min;
    private int max;

    public BubbleBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {

        root = el("g").translate(chart.area("x"), chart.area("y"));

        min = options.optInt("min", 5);
        max = options.optInt("max", 30);
    }

    @Override
    public Object draw() {

        // clip
        root.put("clip-path", chart.clipId());

        JSONArray points = getXY();

        for(int i = 0, len = points.length(); i < len; i++) {
            JSONObject point = points.getJSONObject(i);

            for(int j = 0, jLen = point.getJSONArray("x").length(); j < jLen; j++) {
                JSONObject o = new JSONObject()
                        .put("x", point.getJSONArray("x").getDouble(j))
                        .put("y", point.getJSONArray("y").getDouble(j))
                        .put("value", point.getJSONArray("value").getDouble(j));

                createBubble(o, i);
            }

        }


        return new JSONObject().put("root", root);
    }

    private Transform createBubble(JSONObject o, int i) {
        JSONObject series = chart.series(options.getJSONArray("target").getString(i));
        double radius = getScaleValue(o.getDouble("value"), series.getDouble("min"), series.getDouble("max"), min, max);

        JSONObject circleOpt = new JSONObject()
                .put("cx",o.getDouble("x"))
                .put("cy",o.getDouble("y"))
                .put("r",radius)
                .put("fill",color(i))
                .put("fill-opacity",chart.theme("bubbleOpacity"))
                .put("stroke", color(i))
                .put("stroke-width",chart.theme("bubbleBorderWidth"));

        Transform circle = root.circle(circleOpt);

        return circle;

    }
}
