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
import com.jennifer.ui.util.StringUtil;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class FillGaugeBrush extends DonutBrush {

    private Transform root;
    private double max;
    private  double min;
    private double value;
    private String shape;
    private String direction;
    private String svg;
    private String path;
    private double w;
    private double centerX;
    private double outerRadius;
    private double centerY;
    private String clipId;
    private Transform rect;

    public FillGaugeBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public FillGaugeBrush(ChartBuilder chart, JSONObject options) {
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

        w = min / 2.0;
        centerX = width / 2.0;
        centerY = height / 2.0;
        outerRadius = w;
        clipId = StringUtil.createId("fill-gauge");

        Option o = opt();
        o.put("id", clipId);
        Transform clip = (Transform) chart.defs().clipPath(o);

        Option rectOpt = opt();
        rectOpt.x(0).y(0).width(0).height(0);
        rect = clip.rect(rectOpt);

        this.min = options.optDouble("min", 0);
        max = options.optDouble("max", 100);
        value = options.optDouble("value", 0);

        shape = options.optString("shape", "circle");
        direction = options.optString("direction", "vertical");
        svg = options.optString("svg", "");
        path = options.optString("path", "");

    }

    private void setDirection(String direction) {
        double rate = (value - min) / (max - min);

        double height = chart.height();
        double width = chart.width() * rate;
        double x = 0;
        double y = 0;

        if ("vertical".equals(direction)) {
            height = chart.height() * rate;
            width = chart.width();
            x = 0;
            y = chart.height() - height;
        }

        rect.put("x", x).put("y", y).put("width", width).put("height", height);
    }
    
    public Object draw() {

        setDirection(direction);

        if ("circle".equals(shape)) {

            root.circle(opt()
                .cx(centerX)
                .cy(centerY)
                .r(outerRadius)
                .fill(chart.theme("gaugeBackgroundColor"))
            );

            root.circle(opt()
                .cx(centerX)
                .cy(centerY)
                .r(outerRadius)
                .fill(color(0))
                .clipPath(chart.url(clipId))
            );

        } else if ("rect".equals(shape)) {

            root.rect(opt()
                .x(0)
                .y(0)
                .width(chart.width())
                .height(chart.height())
                .fill(chart.theme("gaugeBackgroundColor"))
            );

            root.rect(opt()
                .x(0)
                .y(0)
                .width(chart.width())
                .height(chart.height())
                .fill(color(0))
                .clipPath(chart.url(clipId))
            );


        } else {
            if (!"".equals(svg)) {
                // TODO: custom svg image load
            } else {

                root.path(opt()
                        .x(0)
                        .y(0)
                        .fill(chart.theme("gaugeBackgroundColor"))
                        .d(path)
                );

                root.path(opt()
                        .x(0)
                        .y(0)
                        .fill(color(0))
                        .clipPath(chart.url(clipId))
                        .d(path)

                );
            }
        }
        
        return opt().put("root", root);
    }


}
