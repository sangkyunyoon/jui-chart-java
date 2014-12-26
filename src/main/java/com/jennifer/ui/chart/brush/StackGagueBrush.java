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
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-27.
 */
public class StackGagueBrush extends DonutBrush {

    private double w;
    private double centerX;
    private double centerY;
    private double outerRadius;
    private Transform root;
    private double min;
    private double max;
    private double size;
    private double startAngle;
    private double endAngle;
    private String target;
    private double cut;
    private String title;

    public StackGagueBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        int width = chart.area("width"), height = chart.area("height");
        int min = width;

        if (height < min) {
            min = height;
        }

        w = min / 2.0;
        centerX = width / 2.0;
        centerY = height / 2.0;
        outerRadius = w;

        root = el("g").translate(chart.area("x"), chart.area("y"));

        this.min =  options.optDouble("min", 0);
        this.max =  options.optDouble("max", 100);
        this.size =  options.optDouble("size", 24);
        this.cut =  options.optDouble("cut", 5);
        this.startAngle =  options.optDouble("startAngle", 0);
        this.endAngle =  options.optDouble("endAngle", 360);
        this.target = options.optString("target");

        this.title = options.optString("title", "");
    }

    @Override
    public Object draw() {

        int count = chart.data().length();
        if (endAngle >= 360) {
            endAngle = 359.99999;
        }

        for(int i = 0, len = count; i < len; i++) {
            double rate = (chart.dataDouble(i, target) - min) / (max - min);
            double currentAngle = (endAngle) * rate;
            double innerRadius = outerRadius - size + cut;


            // 빈 공간 그리기
            Transform g = this.drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle + currentAngle, endAngle - currentAngle, new JSONObject()
                .put("fill",chart.theme("gaugeBackgroundColor"))
            );

            root.append(g);

            // 채워진 공간 그리기
            g = this.drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle, currentAngle, new JSONObject()
                            .put("fill",color(i))
            , true);

            root.append(g);


            Transform text = root.text(new JSONObject()
                .put("x", centerX + 2)
                .put("y", centerY + Math.abs(outerRadius) - 5)
                .put("fill",color(i))
                .put("font-size", "12px")
                .put("font-weight", "bold")
            );

            if(chart.data(i).has(title)) {
                text.textNode(chart.data(i, title).toString());
            } else {
                text.textNode("");
            }

            outerRadius -= size;
        }

        return new JSONObject().put("root", root);
    }
}
