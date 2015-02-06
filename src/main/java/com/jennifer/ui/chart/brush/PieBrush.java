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
import com.jennifer.ui.util.MathUtil;

import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-27.
 */
public class PieBrush extends Brush {

    private double w;
    private double centerX;
    private double centerY;
    private double outerRadius;
    private Transform root;

    public PieBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.area("x"), chart.area("y"));

        // 특정 크기에 맞게 자동으로 파이가 늘어나는 속성
        boolean scalable = this.options.optBoolean("scalable", false);

        int width = chart.area("width"), height = chart.area("height");
        int min = width;
        double xScale = 1;
        double yScale = 1;

        if (scalable) {
            xScale = 1;
            yScale = (height - 120)/width;

            if (height < min) {
                min = height;

                xScale = (width - 120)/height;
                yScale = 1;
            }

            root.scale(xScale, yScale);
        } else {
            if (height < min) {
                min = height;
            }
        }

        // center
        w = min / 2.0;
        centerX = width / 2.0 / xScale;
        centerY = height / 2.0 / yScale;
        outerRadius = w;

    }

    @Override
    public Object draw() {

        double all = 359.99, startAngle = 0, max = 0;

        JSONObject data = chart.data(0);
        JSONArray target = options.getJSONArray("target");

        int maxCount = 0;

        for(int i = 0, len = target.length(); i < len; i++) {
            double value = data.getDouble(target.getString(i));

            max += value;

            if (value > 0) {
                maxCount++;
            }
        }

        if (max == 0) {
            Transform g = drawPie(centerX, centerY, outerRadius, 0, 360, new JSONObject()
                            .put("fill",color(8))
                            .put("stroke", chart.theme("pieBorderColor"))
                            .put("stroke-width",0)
            );

            Transform text = drawText(centerX, centerY, outerRadius, 0, 180, "0");

            g.append(text);

            root.append(g);

        } else {
            for (int i = 0; i < target.length(); i++) {
                double value = data.getDouble(target.getString(i)), endAngle = all * (value / max);

                Transform g = drawPie(centerX, centerY, outerRadius, startAngle, endAngle, new JSONObject()
                                .put("fill",color(i))
                                .put("stroke", chart.theme("pieBorderColor"))
                                .put("stroke-width",0)
                );



                Transform text = drawText(centerX, centerY, outerRadius, startAngle, endAngle, formatNumber(value));

                g.append(text);

                root.append(g);

                startAngle += endAngle;
            }
        }

        return new JSONObject().put("root",root);
    }

    private Transform drawText(double centerX, double centerY, double outerRadius, double startAngle, double endAngle, String text) {

        double innerRadius = outerRadius * 0.62;
        Transform t = this.chart.text(new JSONObject()
                .put("text-anchor", "middle")
                .put("fill", "white")
                , text);

        JSONObject obj = MathUtil.rotate(0, -innerRadius, MathUtil.radian(startAngle));

        double startX = obj.getDouble("x"),
                startY = obj.getDouble("y");

        obj = MathUtil.rotate(startX, startY, MathUtil.radian(endAngle/2));

        t.put("x", obj.getDouble("x"));
        t.put("y", obj.getDouble("y") - 5);

        return t;
    }

    private Transform drawPie(double centerX, double centerY, double outerRadius, double startAngle, double endAngle, JSONObject option) {
        Transform g = (Transform)el("g").put("class", "pie");

        Path path = g.path(option);

        // 바깥 지름 부터 그림
        JSONObject obj = MathUtil.rotate(0, -outerRadius, MathUtil.radian(startAngle));

        double startX = obj.getDouble("x"),
                startY = obj.getDouble("y");

        // 시작 하는 위치로 옮김
        path.MoveTo(startX, startY);

        // outer arc 에 대한 지점 설정
        obj = MathUtil.rotate(startX, startY, MathUtil.radian(endAngle));

        g.translate(centerX, centerY);

        // arc 그림
        path.Arc(outerRadius, outerRadius, 0, (endAngle > 180) ? 1 : 0, 1, obj.getDouble("x"), obj.getDouble("y"));
        path.LineTo(0, 0);
        path.Close();

        // TODO: Text 표시 해야함

        return g;
    }
}
