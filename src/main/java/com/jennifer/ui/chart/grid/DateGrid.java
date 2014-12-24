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
import com.jennifer.ui.util.scale.TimeScale;
import org.json.JSONObject;

import java.util.Date;

import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-24.
 */
public class DateGrid extends Grid {
    private OptionArray ticks;
    private int bar;
    private OptionArray values;

    public DateGrid(Orient orient, ChartBuilder chart, Option options) {
        super(orient, chart, options);
    }

    public DateGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }

    @Override
    public void init() {
        scale = new TimeScale();
    }

    protected void drawTop(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.line();

        if (!hasLine) {
            Option o = opt().x2(chart.width());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(this.values.getDouble(i), 0);

            Option lineOpt = opt().y2(hasLine ? full_height : -bar);

            axis.append(line(lineOpt));

            Option textOpt = opt().x(0).y(-bar - 4).textAnchor("middle").fill(chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }

    }

    protected void drawBottom(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.line();

        if (!hasLine) {
            Option o = opt();
            o.put("x2", chart.width());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(values.getDouble(i), 0);

            Option lineOpt = opt();
            lineOpt.y2(hasLine ? -full_height : bar);

            axis.append(line(lineOpt));

            Option textOpt = opt();
            textOpt.x(0);
            textOpt.y(bar * 3);
            textOpt.textAnchor("middle");
            textOpt.fill(chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }
    }

    protected void drawLeft(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.line();

        if (!hasLine) {
            Option o = opt();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(0, values.getDouble(i));

            Option lineOpt = opt();
            lineOpt.put("x2",hasLine ? full_width : -bar);

            axis.append(line(lineOpt));

            Option textOpt = opt();
            textOpt.put("x", -bar-4);
            textOpt.put("y", bar);
            textOpt.put("text-anchor", "end");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }
    }

    protected void drawRight(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.line();

        if (!hasLine) {
            Option o = opt().y2(chart.height());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(0, values.getDouble(i));

            Option lineOpt = opt();
            lineOpt.put("x2",hasLine ? -full_width : bar);

            axis.append(line(lineOpt));

            Option textOpt = opt();
            textOpt.put("x", bar+4);
            textOpt.put("y", -bar);
            textOpt.put("text-anchor", "start");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }
    }

    private String getFormatString(long d) {
        String text;
        if (options.has("format")) {
            text = TimeUtil.format(d, options.string("format"));
        } else {
            text = TimeUtil.format(d, options.array("step"));
        }
        return text;
    }

    public void drawBefore() {
        initDomain();

        int max = chart.height();

        if (orient == Orient.TOP || orient == Orient.BOTTOM) {
            max = chart.width();
        }

        OptionArray range = (OptionArray)new OptionArray().put(0).put(max);

        TimeScale timeScale = (TimeScale) this.scale;

        timeScale.domain(options.array("domain")).rangeRound(range);

        boolean realtime = options.realtime();
        OptionArray step = JSONUtil.clone(options.array("step"));

        if (realtime) {
            this.ticks = timeScale.realTicks(step.string(0), step.I(1));
        } else {
            this.ticks = timeScale.ticks(step.string(0), step.I(1));
        }

        // step = [this.time.days, 1];
        this.bar = 6;

        this.values = new OptionArray();

        for (int i = 0, len = this.ticks.length(); i < len; i++) {
            this.values.put(this.scale.get(this.ticks.D(i)));
        }
    }

    private void initDomain() {


        if (has("target") && !has("domain")) {

            if (options.get("target") instanceof String) {
                OptionArray list = new OptionArray();
                list.put(options.string("target"));
                options.put("target", list);
            }

            OptionArray target = (OptionArray) options.array("target");
            OptionArray domain = new OptionArray();
            OptionArray data = chart.data();

            long min = 0;
            long max = 0;

            boolean hasMin = options.has("min");
            boolean hasMax = options.has("max");

            for (int i = 0, len = target.length(); i < len; i++) {
                String key = target.getString(i);

                for(int index = 0, dataLength = data.length(); index < dataLength; index++) {
                    Option row = (Option) data.object(index);

                    long value = 0;

                    if (row.get(key) instanceof Date) {
                        value = ((Date) row.get(key)).getTime();
                    } else {
                        value = row.getLong(key);
                    }

                    if (!hasMin) {
                        min = value;
                        hasMin = true;
                    }
                    else if (min > value) min = value;

                    if (!hasMax) {
                        max = value;
                        hasMax = true;
                    }
                    else if (max < value) max = value;
                }

            }

            options.put("max", max);
            options.put("min", min);

            domain.put(min).put(max);

            options.domain(domain);

            if (options.reverse()) {
                JSONUtil.reverse(domain);
            }
        }

    }

    public Object draw() {
        return this.drawGrid();
    }
}
