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

package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.MathUtil;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class RadarGrid extends BlockGrid{
    private Transform root;
    private JSONArray domain;
    private int step;
    private double unit;
    private double centerX;
    private double centerY;
    private int count;
    private double h;
    private double w;
    private boolean hideText;
    private boolean line;
    private double max;
    private double stepValue;
    private boolean extra;
    private String shape;

    protected String DEFAULT_FORMAT = "###";  // DecimalFormat

    public RadarGrid(Orient orient, ChartBuilder chart, Option options) {
        super(orient, chart, options);
    }

    public RadarGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }

    @Override
    public void drawBefore() {
        initDomain();

        int width = chart.width(), height = chart.height();
        int min = width;

        if (height < min) {
            min = height;
        }

        // default value ;
        domain = options.array("domain");
        step = options.optInt("step", 10);


        // center
        w = min / 2.0;
        centerX = chart.x() + width / 2.0;
        centerY = chart.y() + height / 2.0;
        count = domain.length();
        unit = 2 * Math.PI / count;
        h = Math.abs(w) / step;
        hideText = options.optBoolean("hideText", false);
        line = options.optBoolean("line", true);
        extra = options.optBoolean("extra", false);
        shape = options.optString("shape", "radial");
        max = options.optDouble("max", 100);
        stepValue = max / step;

    }

    public void drawCustom(Transform root) {
        this.root = root;

        double width = chart.width(), height = chart.height();

        double startY = -w;
        double startX = 0;

        OptionArray position = new OptionArray();
        for (int i = 0; i < count; i++) {
            double x2 = centerX + startX, y2 = centerY + startY;

            root.line(opt()
                .x1(centerX)
                .y1(centerY)
                 .x2(x2)
                 .y2(y2)
                 .stroke(chart.theme("gridAxisBorderColor"))
                 .strokeWidth(chart.theme("gridBorderWidth"))
            );

            position.put(opt()
                .x1(centerX)
                .y1(centerY)
                .x2(x2)
                .y2(y2)
            );

            double ty = y2, tx = x2;
            String talign = "middle";

            if (y2 > centerY) {
                ty = y2 + 20;
            } else if (y2 < centerY) {
                ty = y2 - 10;
            }

            if (x2 > centerX + 10) {
                talign = "start";
                tx += 10;
            } else if (x2 < centerX - 10) {
                talign = "end";
                tx -= 10;
            } else {
                talign = "middle";
            }

            if (!hideText) {
                root.text(opt()
                    .x(tx)
                    .y(ty)
                    .textAnchor(talign)
                    .fill(chart.theme("gridFontColor"))
                ).textNode(domain.getString(i));
            }

            Option obj = MathUtil.rotate(startX, startY, unit);

            startX = obj.x();
            startY = obj.y();

        }

        if (line) {
            // area split line
            startY = -w;
            double stepBase = 0;

            for (int i = 0; i < step; i++) {

                if (i == 0 && extra) {
                    startY += h;
                    continue;
                }

                if (shape == "circle") {
                    drawCircle(centerX, centerY, 0, startY, count);
                } else {
                    drawRadial(centerX, centerY, 0, startY, count, unit);
                }

                if (!hideText) {

                    root.text(opt()
                                    .x(centerX)
                                    .y(centerY + (startY+5))
                                    .fontSize(12)
                                    .fill(chart.theme("gridFontColor"))
                    ).textNode(getFormatString(max - stepBase));
                }
                startY += h;
                stepBase += stepValue;
            }
        }
    }

    private void drawCircle(double centerX, double  centerY, double x, double y, int count) {
        root.circle(opt()
            .cx(centerX)
            .cy(centerY)
            .r(Math.abs(y))
            .fillOpacity(0)
            .stroke(chart.theme("gridAxisBorderColor"))
            .strokeWidth(chart.theme("gridBorderWidth"))
        );
    }

    private void drawRadial(double centerX, double  centerY, double x, double y, int count, double unit) {
        Transform group = root.group();
        OptionArray points = new OptionArray();

        points.put(new OptionArray().put(centerX + x).put(centerY + y));

        double startX = x;
        double startY = y;

        for(int i = 0; i < count; i++) {
            Option obj = MathUtil.rotate(startX, startY, unit);

            startX = obj.x();
            startY = obj.y();

            points.put(new OptionArray().put(centerX + startX).put(centerY + startY));
        }

        Path path = group.path(opt()
            .fill("none")
            .stroke(chart.theme("gridAxisBorderColor"))
            .strokeWidth(chart.theme("gridBorderWidth"))
        );

        for(int i = 0, len = points.length(); i < len; i++) {
            OptionArray point = (OptionArray) points.array(i);

            if (i == 0) {
                path.MoveTo(point.D(0), point.D(1));
            } else {
                path.LineTo(point.D(0), point.D(1));
            }
        }

        path.LineTo(points.array(0).getDouble(0), points.array(0).getDouble(1));
    }

    public Option get(int index, double value) {

        double rate = value / max;

        double height = Math.abs(w);
        double pos = height * rate;

        double y = -pos, x = 0;

        Option o = MathUtil.rotate(x, y, unit * index);

        return opt().x(height + o.x()).y(height + o.y());
    }

    public Object draw() {
        return this.drawGrid();
    }
    @Override
    protected String getFormatString(Object value) {

        DecimalFormat df = new DecimalFormat(options.optString("format", DEFAULT_FORMAT));

        return df.format(Double.valueOf(value.toString()));
    }
}
