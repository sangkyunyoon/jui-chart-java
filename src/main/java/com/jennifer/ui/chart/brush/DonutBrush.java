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

import com.jennifer.ui.Main;
import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.MathUtil;

import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-27.
 */
public class DonutBrush extends Brush {
    private int width;
    private int height;
    private int min;
    private double w;
    private double centerX;
    private double centerY;
    private double startY;
    private double startX;
    private double outerRadius;
    private double innerRadius;
    private Transform root;

    public DonutBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.area("x"), chart.area("y"));
        width = chart.area("width");
        height = chart.area("height");
        min = width;

        if (height < min) {
            min = height;
        }

        // center
        w = min / 2.0;
        centerX = width / 2.0;
        centerY = height / 2.0;
        startY = -w;
        startX = 0;
        outerRadius = Math.abs(startY);
        innerRadius = outerRadius - options.optInt("size", 10);


    }

    @Override
    public Object draw() {

        String target = options.getJSONArray( "target").getString(0);

        JSONObject s = chart.series(target);

        double all = 360.0;
        double startAngle = 0;

        double max = 0;
        JSONArray data = chart.data();
        for(int i = 0, len = data.length(); i < len; i++) {
            max = max +  chart.dataDouble(i,  target);
        }

        for(int i = 0, len = data.length(); i < len; i++) {
            double value = chart.dataDouble(i, target);
            double endAngle = all * (value / max);

            Transform g = drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, new JSONObject()
                    .put("fill",color(i))
                    .put("stroke", chart.theme("donutBorderColor"))
                    .put("stroke-width",chart.theme("donutBorderWidth"))
            );

            root.append(g);

            startAngle += endAngle;
        }



        return new JSONObject().put("root", root);
    }

    protected Transform drawDonut(double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, JSONObject attrs) {
        return drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, attrs, false);
    }

    protected Transform drawDonut(double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, JSONObject attrs, boolean hasCircle) {

        double dist = Math.abs(outerRadius - innerRadius);

        Transform g = (Transform) el("g").put("class", "donut");
        Path path = g.path(attrs);

        JSONObject obj =  MathUtil.rotate(0.0, -outerRadius, MathUtil.radian(startAngle));

        double startX = obj.getDouble("x");
        double startY = obj.getDouble("y");

        JSONObject innerCircle =  MathUtil.rotate(0, -innerRadius, MathUtil.radian(startAngle));

        double startInnerX = innerCircle.getDouble("x");
        double startInnerY = innerCircle.getDouble("y");

        path.MoveTo(startX, startY);

        obj =  MathUtil.rotate(startX, startY, MathUtil.radian(endAngle));

        innerCircle =  MathUtil.rotate(startInnerX, startInnerY, MathUtil.radian(endAngle));

        // move to center
        g.translate(centerX, centerY);

        // draw outer arc
        path.Arc(outerRadius, outerRadius, 0, (endAngle > 180) ? 1 : 0, 1, obj.getDouble("x"), obj.getDouble("y"));

        // draw line
        path.LineTo(innerCircle.getDouble("x"), innerCircle.getDouble("y"));

        // draw inner arc
        path.Arc(innerRadius, innerRadius, 0, (endAngle > 180) ? 1 : 0, 0, startInnerX, startInnerY);

        // close path
        path.Close();

        return g;
    }
}
