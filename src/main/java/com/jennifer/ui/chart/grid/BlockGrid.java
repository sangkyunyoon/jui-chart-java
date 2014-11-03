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
import com.jennifer.ui.util.OptionArray;
import com.jennifer.ui.util.scale.OrdinalScale;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

import static com.jennifer.ui.util.Option.opt;

/**
 *
 * new BlockGrid().title().domain().target()
 *
 * Created by Jayden on 2014-10-24.
 */
public class BlockGrid extends Grid {

    private OptionArray points;
    private OptionArray domain;
    private double band;
    private double half_band;
    private int bar;

    public BlockGrid(Orient orient, ChartBuilder chart, Option options) {
        super(orient, chart, options);
    }

    public BlockGrid(Orient orient, ChartBuilder chart, JSONObject options) {

        super(orient, chart, options);


        ;

    }


    @Override
    public void init() {
        scale = new OrdinalScale();
    }


    protected void drawTop(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            Option o = new Option().x2(chart.width());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.string(i);

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(this.points.D(i), 0);

            Option lineOpt = new Option()
                    .x1(-half_band)
                    .y1(0)
                    .x2(-half_band)
                    .y2(hasLine ? full_height : -this.bar);

            axis.append(this.line(lineOpt));

            Option textOpt = new Option()
                .x(0)
                .y(-20)
                .textAnchor("middle")
                .fill(chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(chart.width(), 0);

            Option lineOpt = new Option().y2(hasLine ? full_height : -this.bar);

            axis.append(this.line(lineOpt));
        }
    }

    protected void drawBottom(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            Option o = new Option();
            o.put("x2", chart.width());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.string(i);

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(this.points.D(i), 0);

            Option lineOpt = new Option();
            lineOpt.put("x1",-this.half_band);
            lineOpt.put("y1",0);
            lineOpt.put("x2",-this.half_band);
            lineOpt.put("y2",hasLine ? -full_height : this.bar);

            axis.append(this.line(lineOpt));

            Option textOpt = new Option();
            textOpt.put("x", 0);
            textOpt.put("y", 20);
            textOpt.put("text-anchor", "middle");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(chart.width(), 0);

            Option lineOpt = new Option();
            lineOpt.put("y2", hasLine ? -full_height : this.bar);

            axis.append(this.line(lineOpt));
        }
    }

    protected void drawLeft(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            Option o = opt();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.string(i);

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(0, this.points.D(i) - this.half_band);

            Option lineOpt = opt();
            lineOpt.put("x2",hasLine ? full_width : -this.bar);

            axis.append(this.line(lineOpt));

            Option textOpt = opt();
            textOpt.put("x", -this.bar - 4);
            textOpt.put("y", this.half_band);
            textOpt.put("text-anchor", "end");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(0, chart.height());

            Option lineOpt = opt();
            lineOpt.put("x2", hasLine ? full_width : -this.bar);

            axis.append(this.line(lineOpt));
        }
    }


    protected void drawRight(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            Option o = opt();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.string(i);

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(0, this.points.D(i) - this.half_band);

            Option lineOpt = opt();
            lineOpt.put("x2",hasLine ? -full_width : this.bar);

            axis.append(this.line(lineOpt));

            Option textOpt = opt();
            textOpt.put("x", this.bar + 4);
            textOpt.put("y", this.half_band);
            textOpt.put("text-anchor", "start");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(0, chart.height());

            Option lineOpt = opt();
            lineOpt.put("x2", hasLine ? -full_width : this.bar);

            axis.append(this.line(lineOpt));
        }
    }

    public boolean full() {
        return options.optBoolean("full", false);
    }

    public boolean reverse() {

        return options.optBoolean("reverse", false);
    }

    public void drawBefore() {
        initDomain();

        int width = chart.width();
        int height = chart.height();
        int max = (orient == Orient.LEFT || orient == Orient.RIGHT) ? height : width;

        this.scale.domain(options.array("domain"));

        OptionArray range = new OptionArray();
        range.put(0).put(max);
        if (this.full()) {
            scale.rangeBands(range, 0, 0);
        } else {
            scale.rangePoints(range, 0);
        }

        this.points = this.scale.range();
        this.domain = this.scale.domain();
        this.band = this.scale.rangeBand();
        this.half_band = ((this.full()) ? 0 : (this.band / 2));
        this.bar = 6;
    }

    private void initDomain() {

        if (has("target") && !has("domain")) {

            OptionArray domain = new OptionArray();
            OptionArray data = chart.data();

            int start = 0;
            int end = data.length() - 1;
            int step = 1;

            boolean reverse = options.optBoolean("reverse", false);

            if (reverse) {
                start = data.length() - 1;
                end = 0;
                step = 1;
            }

            for(int i = start; ((reverse) ? i >= end : i <= end); i += step) {
                domain.put(((Option)data.object(i)).string(options.string("target")));
            }

            options.put("domain", domain);
            options.put("step", options.optInt("step", 10));
            options.put("max", options.optInt("max", 100));

        }

    }

    public Object draw() {
        return this.drawGrid();
    }
}
