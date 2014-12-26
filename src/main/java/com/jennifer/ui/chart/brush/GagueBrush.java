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
import com.jennifer.ui.util.MathUtil;

import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-27.
 */
public class GagueBrush extends DonutBrush {

    private Transform root;
    private double min;
    private double max;
    private double value;
    private double rate;
    private double size;
    private double startAngle;
    private double endAngle;
    private boolean arrow;
    private String unitText;
    private double w;
    private double centerX;
    private double centerY;
    private double outerRadius;
    private double innerRadius;

    public GagueBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }


    @Override
    public void drawBefore() {
        root = el("g").translate(chart.area("x"), chart.area("y"));

        int width = chart.area("width"), height = chart.area("height");
        int min = width;

        if (height < min) {
            min = height;
        }

        w = min / 2.0;
        centerX = width / 2.0;
        centerY = height / 2.0;
        outerRadius = w;


        // default value load
        this.min =  options.optDouble("min", 0);
        this.max =  options.optDouble("max", 100);
        this.value =  options.optDouble("value", 0);
        this.rate =  options.optDouble("rate", 100);
        this.size =  options.optDouble("size", 60);
        this.startAngle =  options.optDouble("startAngle", 0);
        this.endAngle =  options.optDouble("endAngle", 360);
        this.arrow =  options.optBoolean("arrow", true);
        this.unitText =  options.optString("unitText", "");

        innerRadius = outerRadius - size;

    }

    @Override
    public Object draw() {

        double rate = (value - min) / (max - min);
        double currentAngle = (endAngle) * rate;

        if (endAngle >= 360) {
            endAngle = 359.99999;
        }

        Transform g = this.drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle + currentAngle, endAngle - currentAngle, new JSONObject().put("fill",chart.theme("gaugeBackgroundColor")));

        root.append(g);

        g = this.drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle, currentAngle, new JSONObject().put("fill",color(0)));

        root.append(g);

        if (arrow) {
            g = createArrow(startAngle, currentAngle);
            root.append(g);
        }

        g = createText(startAngle, endAngle, min, max, value);
        root.append(g);

        return new JSONObject().put("root", root);
    }

    private Transform createText(double startAngle, double endAngle, double min, double max, double value) {
        Transform g = el("g").translate(centerX, centerY);

        g.text(new JSONObject()
            .put("x", 0)
            .put("y", (arrow) ? 70 : 10)
            .put("text-anchor","middel")
            .put("font-family", chart.theme("fontFamily"))
            .put("font-size", "3em")
            .put("font-weight", 1000)
            .put("fill",color(0))
        ).textNode(value+"");

        if (!"".equals(unitText)) {
            g.text(new JSONObject()
                .put("x", 0)
                .put("y", 100)
                .put("text-anchor","middel")
                .put("font-family", chart.theme("fontFamily"))
                .put("font-size", "1.5em")
                .put("font-weight", 500)
                .put("fill",chart.theme("gaugeFontColor"))
            ).textNode(unitText);
        }

        // 바깥 지름 부터 그림
        double startX = 0;
        double startY = -outerRadius;

        // min
        JSONObject obj = MathUtil.rotate(startX, startY, MathUtil.radian(startAngle));

        startX = obj.getDouble("x");
        startY = obj.getDouble("y");

        g.text(new JSONObject()
            .put("x", obj.getDouble("x") + 30)
            .put("y", obj.getDouble("y") + 20)
            .put("text-anchor","middel")
            .put("font-family", chart.theme("fontFamily"))
            .put("fill",chart.theme("gaugeFontColor"))
        ).textNode(min+"");


        // max
        // outer arc 에 대한 지점 설정
        obj = MathUtil.rotate(startX, startY, MathUtil.radian(endAngle));

        g.text(new JSONObject()
            .put("x", obj.getDouble("x") + 30)
            .put("y", obj.getDouble("y") + 20)
            .put("text-anchor","middel")
            .put("font-family", chart.theme("fontFamily"))
            .put("fill",chart.theme("gaugeFontColor"))
        ).textNode(max+"");

        return g;
    }

    private Transform createArrow(double startAngle, double currentAngle) {
        Transform g = el("g").translate(centerX, centerY);

        double startX = 0;
        double startY = -(outerRadius + 5);

        Path path = g.path(new JSONObject()
            .put("stroke", chart.theme("gaugeArrowColor"))
            .put("stroke-width",0.2)
            .put("fill",chart.theme("gaugeArrowColor"))
        );

        path.MoveTo(startX, startY);
        path.LineTo(5, 0);
        path.LineTo(-5, 0);
        path.Close();

        // start angle
        path.rotate(startAngle);
        path.rotate(endAngle + startAngle);

        g.circle(new JSONObject().put("cx",0).put("cy",0).put("r",5).put("fill",chart.theme("gaugeArrowColor")));
        g.circle(new JSONObject().put("cx",0).put("cy",0).put("r",2).put("fill",chart.theme("gaugeArrowColor")));

        return g;
    }
}
