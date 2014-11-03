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
public class PieBrush extends Brush {

    private double w;
    private double centerX;
    private double centerY;
    private double outerRadius;
    private Transform root;

    public PieBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public PieBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());

        int width = chart.width(), height = chart.height();
        int min = width;

        if (height < min) {
            min = height;
        }

        // center
        w = min / 2.0;
        centerX = width / 2.0;
        centerY = height / 2.0;
        outerRadius = w;

    }

    @Override
    public Object draw() {

        String target = options.array("target").getString(0);
        Option s = chart.series(target);

        double all = 360, startAngle = 0;

        double max = 0;
        JSONArray data = s.array("data");
        for (int i = 0; i < data.length(); i++) {
            max += data.getDouble(i);
        }

        for (int i = 0; i < data.length(); i++) {
            double row = data.getDouble(i), endAngle = all * (row / max);

            Transform g = drawPie(centerX, centerY, outerRadius, startAngle, endAngle, opt()
                            .fill(color(i))
                            .stroke(chart.theme("pieBorderColor"))
                            .strokeWidth(chart.theme("pieBorderWidth"))
            );


            root.append(g);

            startAngle += endAngle;
        }

        return opt().put("root",root);
    }

    private Transform drawPie(double centerX, double centerY, double outerRadius, double startAngle, double endAngle, Option option) {
        Transform g = (Transform)el("g").put("class", "pie");

        Path path = g.path(option);

        // 바깥 지름 부터 그림
        Option obj = MathUtil.rotate(0, -outerRadius, MathUtil.radian(startAngle));

        double startX = obj.x(),
                startY = obj.y();

        // 시작 하는 위치로 옮김
        path.MoveTo(startX, startY);

        // outer arc 에 대한 지점 설정
        obj = MathUtil.rotate(startX, startY, MathUtil.radian(endAngle));

        g.translate(centerX, centerY);

        // arc 그림
        path.Arc(outerRadius, outerRadius, 0, (endAngle > 180) ? 1 : 0, 1, obj.x(), obj.y());
        path.LineTo(0, 0);
        path.Close();

        return g;
    }
}
