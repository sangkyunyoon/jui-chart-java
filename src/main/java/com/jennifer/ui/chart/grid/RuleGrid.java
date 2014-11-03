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
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-24.
 */
public class RuleGrid extends RangeGrid {

    private boolean hideZero;
    private boolean center;

    public RuleGrid(Orient orient, ChartBuilder chart, Option options) {
        super(orient, chart, options);
    }

    public RuleGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }


    @Override
    protected void drawTop(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? half_height : 0;

        Option o = new Option();
        o.put("y1", centerPosition);
        o.put("y2", centerPosition);
        o.put("x2", chart.width());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(this.values.getDouble(i), centerPosition );

            Option lineOpt = new Option();
            lineOpt.put("y1",center ? -bar : 0);
            lineOpt.put("y2",bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                Option textOpt = new Option();
                textOpt.put("x", 0);
                textOpt.put("y", bar*2 + 4);
                textOpt.put("text-anchor", "middle");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }
    }

    @Override
    protected void drawBottom(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? -half_height : 0;

        Option o = new Option();
        o.put("y1", centerPosition);
        o.put("y2", centerPosition);
        o.put("x2", chart.width());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(this.values.getDouble(i), centerPosition );

            Option lineOpt = new Option();
            lineOpt.put("y1",center ? -bar : 0);
            lineOpt.put("y2",center ? bar : -bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                Option textOpt = new Option();
                textOpt.put("x", 0);
                textOpt.put("y", -bar*2);
                textOpt.put("text-anchor", "middle");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }
    }

    @Override
    protected void drawLeft(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? half_width : 0;

        Option o = new Option();
        o.put("x1", centerPosition);
        o.put("x2", centerPosition);
        o.put("y2", chart.height());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(centerPosition, this.values.getDouble(i));

            Option lineOpt = new Option();
            lineOpt.put("x1",center ? -bar : 0);
            lineOpt.put("x2",bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                Option textOpt = new Option();
                textOpt.put("x", 2*bar);
                textOpt.put("y", bar-2);
                textOpt.put("text-anchor", "start");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }
    }


    @Override
    protected void drawRight(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? -half_width : 0;

        Option o = new Option();
        o.put("x1", centerPosition);
        o.put("x2", centerPosition);
        o.put("y2", chart.width());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(centerPosition, this.values.getDouble(i));

            Option lineOpt = new Option();
            lineOpt.put("x1",center ? -bar : 0);
            lineOpt.put("x2",center ? bar : -bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                Option textOpt = new Option();
                textOpt.put("x", -bar - 4 );
                textOpt.put("y", bar-2);
                textOpt.put("text-anchor", "middle");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }

    }

    public void drawBefore() {
        super.drawBefore();

        this.hideZero = options.optBoolean("hideZero", false);
        this.center = options.optBoolean("center", false);

    }

}
