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
import com.jennifer.ui.util.ChartDateFormat;
import com.jennifer.ui.util.ChartStringFormat;


import com.jennifer.ui.util.scale.OrdinalScale;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;



/**
 *
 * new BlockGrid().title().domain().getJSONArray("target")
 *
 * Created by Jayden on 2014-10-24.
 */
public class BlockGrid extends Grid {

    private JSONArray points;
    private JSONArray domain;
    private double band;
    private double half_band;
    private int bar;

    public BlockGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }


    @Override
    public void init() {
        scale = new OrdinalScale();
    }


    protected void drawTop(Transform root) {
        int full_height = chart.area("height");

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            JSONObject o = new JSONObject().put("x2", chart.area("width"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            String domain = getFormatString(this.domain.getString(i));

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(this.points.getDouble(i), 0);

            JSONObject lineOpt = new JSONObject()
                    .put("x1", -half_band)
                    .put("y1", 0)
                    .put("x2", -half_band)
                    .put("y2", hasLine ? full_height : -this.bar);

            axis.append(this.line(lineOpt));

            JSONObject textOpt = new JSONObject()
                .put("x", 0)
                .put("y", -20)
                .put("text-anchor", "middle")
                .put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(chart.area("width"), 0);

            JSONObject lineOpt = new JSONObject().put("y2", hasLine ? full_height : -this.bar);

            axis.append(this.line(lineOpt));
        }
    }

    protected void drawBottom(Transform root) {
        int full_height = chart.area("height");

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("x2", chart.area("width"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            String domain = getFormatString(this.domain.getString(i));

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(this.points.getDouble(i), 0);

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x1",-this.half_band);
            lineOpt.put("y1",0);
            lineOpt.put("x2",-this.half_band);
            lineOpt.put("y2",hasLine ? -full_height : this.bar);

            axis.append(this.line(lineOpt));

            JSONObject textOpt = new JSONObject();
            textOpt.put("x", 0);
            textOpt.put("y", 20);
            textOpt.put("text-anchor", "middle");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(chart.area("width"), 0);

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("y2", hasLine ? -full_height : this.bar);

            axis.append(this.line(lineOpt));
        }
    }

    protected void drawLeft(Transform root) {
        int full_width = chart.area("width");

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("y2", chart.area("height"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            String domain = getFormatString(this.domain.getString(i));

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(0, this.points.getDouble(i) - this.half_band);

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2",hasLine ? full_width : -this.bar);

            axis.append(this.line(lineOpt));

            JSONObject textOpt = new JSONObject();
            textOpt.put("x", -this.bar - 4);
            textOpt.put("y", this.half_band);
            textOpt.put("text-anchor", "end");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(0, chart.area("height"));

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2", hasLine ? full_width : -this.bar);

            axis.append(this.line(lineOpt));
        }
    }

    protected String getFormatString(String value) {

        if (options.get("format") instanceof ChartDateFormat) {
            ChartDateFormat format = (ChartDateFormat)options.get("format");
            return format.format(Long.parseLong(value));

        } else {
            return value.toString();
        }
    }


    protected void drawRight(Transform root) {
        int full_width = chart.area("width");

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("y2", chart.area("height"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            String domain = getFormatString(this.domain.getString(i));

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(0, this.points.getDouble(i) - this.half_band);

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2",hasLine ? -full_width : this.bar);

            axis.append(this.line(lineOpt));

            JSONObject textOpt = new JSONObject();
            textOpt.put("x", this.bar + 4);
            textOpt.put("y", this.half_band);
            textOpt.put("text-anchor", "start");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, domain));
        }

        if (!hasFull) {
            Transform axis = root.group().translate(0, chart.area("height"));

            JSONObject lineOpt = new JSONObject();
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

        int width = chart.area("width");
        int height = chart.area("height");
        int max = (orient == Orient.LEFT || orient == Orient.RIGHT) ? height : width;

        this.scale.domain(options.getJSONArray("domain"));

        JSONArray range = new JSONArray();
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

    protected void initDomain() {

        if (has("target") && !has("domain")) {

            JSONArray domain = new JSONArray();
            JSONArray data = chart.data();

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
                domain.put((data.getJSONObject( i)).get(options.getString("target")).toString());
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
