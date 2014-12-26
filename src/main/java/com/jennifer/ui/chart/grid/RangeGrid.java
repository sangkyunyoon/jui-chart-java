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
import com.jennifer.ui.util.*;
import com.jennifer.ui.util.dom.Transform;
import com.jennifer.ui.util.scale.LinearScale;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-24.
 */
public class RangeGrid extends Grid {
    protected int step;
    protected boolean nice;
    protected OptionArray ticks;
    protected int bar;

    protected OptionArray values;
    protected String DEFAULT_FORMAT = "###";  // DecimalFormat

    public RangeGrid(Orient orient, ChartBuilder chart, Option options) {
        super(orient, chart, options);
    }

    public RangeGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }


    public void init() {
        scale = new LinearScale();
   }

    @Override
    protected void drawTop(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            root.append(this.axisLine(opt().x2(chart.width())));
        }

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0 && ticks.getDouble(i) != min);

            Transform axis = root.group().translate(this.values.getDouble(i), 0);

            Option lineOpt = new Option();
            lineOpt.put("y2",hasLine ? full_height : -bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            Option textOpt = new Option();
            textOpt.put("x", 0);
            textOpt.put("y", -bar - 4);
            textOpt.put("text-anchor", "middle");
            textOpt.put("fill", chart.theme(isZero, "gridActiveFontColor", "gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
        }
    }

    @Override
    protected void drawBottom(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            Option o = new Option();
            o.put("x2", chart.width());
            root.append(this.axisLine(o));
        }

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0 && ticks.getDouble(i) != min);

            Transform axis = root.group().translate(this.values.getDouble(i), 0);

            Option lineOpt = new Option();
            lineOpt.put("y2",hasLine ? -full_height : bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            Option textOpt = new Option();
            textOpt.put("x", 0);
            textOpt.put("y", bar * 3);
            textOpt.put("text-anchor", "middle");
            textOpt.put("fill", chart.theme(isZero, "gridActiveFontColor", "gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
        }

    }

    @Override
    protected void drawLeft(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            Option o = new Option();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0 && ticks.getDouble(i) != min);

            Transform axis = root.group().translate(0, values.getDouble(i));

            Option lineOpt = new Option();
            lineOpt.put("x2",hasLine ? full_width : -bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!options.optBoolean("hideText", false)) {

                Option textOpt = new Option();
                textOpt.put("x", -bar -4 );
                textOpt.put("y", bar);
                textOpt.put("text-anchor", "end");
                textOpt.put("fill", chart.theme(isZero, "gridActiveFontColor", "gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));

            }
        }

    }


    @Override
    protected void drawRight(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            Option o = new Option();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0 && ticks.getDouble(i) != min);

            Transform axis = root.group().translate(0, values.getDouble(i));

            Option lineOpt = new Option();
            lineOpt.put("x2",hasLine ? -full_width : bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!options.optBoolean("hideText", false)) {

                Option textOpt = new Option();
                textOpt.put("x", bar + 4 );
                textOpt.put("y", bar);
                textOpt.put("text-anchor", "start");
                textOpt.put("fill", chart.theme(isZero, "gridActiveFontColor", "gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));

            }
        }

    }

    public void drawBefore() {
        initDomain();

        int width = chart.width(), height = chart.height();
        scale.domain(JSONUtil.clone(options.array("domain")));

        if (orient == Orient.LEFT || orient == Orient.RIGHT) {
            this.scale.range((OptionArray)new OptionArray().put(height).put(0));
        } else {
            this.scale.range((OptionArray)new OptionArray().put(0).put(width));
        }

        this.step = options.optInt("step", 10);
        this.nice = options.optBoolean("nice", false);
        this.ticks = ((LinearScale)scale).ticks(this.step, this.nice);
        this.bar = 6;

        this.values = new OptionArray();

        for (int i = 0, len = ticks.length(); i < len; i++) {
            this.values.put(this.scale.get(this.ticks.getDouble(i)));
        }

        checkValueFormat(ticks);

    }

    private void checkValueFormat(JSONArray ticks) {

        int doubleCount = 0;
        for(int i = 0, len = ticks.length(); i < len; i++) {
            if (ticks.getDouble(i) != ticks.getInt(i)) doubleCount++;
        }

        if (!options.has("format")) {
            if (doubleCount > 0) {
                options.put("format", "###.##");
            } else {
                options.put("format", "###");
            }
        }

    }

    private void initDomain() {

        if (has("target") && !has("domain")) {

            double size = 0;
            if (orient == Orient.LEFT || orient == Orient.RIGHT) {
                size = chart.height();
            } else {
                size = chart.width();
            }

            if (options.get("target") instanceof String) {
                OptionArray list = new OptionArray();
                list.put(options.string("target"));
                options.put("target", list);
            }

            double max = options.optDouble("max", 0);
            double min = options.optDouble("min", 0);

            JSONArray target = options.array("target");
            OptionArray domain = new OptionArray();
            Option series = chart.series();

            for (int i = 0, len = target.length(); i < len; i++) {
                String key = target.getString(i);

                for(int j = 0, jLen = chart.data().length(); j < jLen; j++) {
                    double value  = chart.dataDouble(j, key);

                    if (value > max) max = value;
                    else if (value < min) min = value;
                }
            }

            options.put("max", max);
            options.put("min", min);
            options.put("step", options.optInt("step", 10));

            double unit = 0;

            if (options.get("unit") instanceof ChartUnit) {
                ChartUnit chartUnit = (ChartUnit)options.get("unit");
                unit = chartUnit.getUnit(Math.abs(max - min), size);
            } else {
                unit = options.optDouble("unit", Math.ceil((max - min) / options.D("step")));
            }


            double start = 0;
            while (start < max) {
                start += unit;
            }

            double end = 0;
            while (end > min) {
                end -= unit;
            }

            if (unit == 0) {
                domain.put(0).put(0);
            } else {
                domain.put(end).put(start);

                if (options.optBoolean("reverse", false)) {
                    JSONUtil.reverse(domain);
                }

                options.put("step", Math.abs(start / unit) + Math.abs(end / unit));

            }

            options.put("domain", domain);


        }

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
