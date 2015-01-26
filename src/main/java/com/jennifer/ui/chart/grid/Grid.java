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

import com.jennifer.ui.chart.AbstractDraw;
import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.ChartTextRotate;
import com.jennifer.ui.util.DomUtil;
import com.jennifer.ui.util.JSONUtil;

import com.jennifer.ui.util.scale.Scale;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-10-24.
 */
public abstract class Grid extends AbstractDraw {

    protected Scale scale;
    protected Orient orient;
    protected ChartBuilder chart;
    protected JSONObject options;

    public Grid(Orient orient, ChartBuilder chart, JSONObject options) {
        this.chart = chart;
        this.orient = orient;
        this.options = options;

        init();
    }
    public void init() {

    }

    public Scale scale() {
        return scale;
    }

    @Override
    public void drawBefore() {

    }

    @Override
    public Object draw() {
        return null;
    }

    protected boolean has(String key) {
        return options.has(key);
    }

    protected String theme(String key) { return chart.theme(key); }
    protected String theme(boolean checked, String key1, String key2) { return chart.theme(checked, key1, key2); }

    protected DomUtil getTextRotate(Transform textElement) {

        Object rotate = options.opt("textRotate");

        if (rotate == null) {
            return textElement;
        }

        double angle = 0;

        if (rotate instanceof ChartTextRotate) {
            angle = ((ChartTextRotate)rotate).run(chart, this, textElement);
        } else {
            angle = ((Integer)rotate).doubleValue();
        }

        double x = textElement.getDouble("x");
        double y = textElement.getDouble("y");

        textElement.rotate(angle, x, y);

        return textElement;
    }

    protected Transform axisLine(JSONObject attr) {

        JSONObject o = new JSONObject();
        o.put("x1", 0);
        o.put("y1", 0);
        o.put("x2", 0);
        o.put("y2", 0);
        o.put("stroke", chart.theme("gridAxisBorderColor"));
        o.put("stroke-width", chart.theme("gridAxisBorderWidth"));
        o.put("stroke-opacity", 1);

        return el("line", JSONUtil.extend(o, attr));
    }

    protected Transform line(JSONObject attr) {
        JSONObject o = new JSONObject();
        o.put("x1", 0);
        o.put("y1", 0);
        o.put("x2", 0);
        o.put("y2", 0);
        o.put("stroke", chart.theme("gridBorderColor"));
        o.put("stroke-width", chart.theme("gridBorderWidth"));
        o.put("stroke-dasharray", chart.theme("gridBorderDashArray"));
        o.put("stroke-opacity", 1);

        return el("line", JSONUtil.extend(o, attr));
    }

    protected Object drawGrid() {
        JSONObject result = new JSONObject();

        JSONObject o = new JSONObject();
        o.put("class", "grid " + options.optString("type", "block"));
        Transform root = el("g", o);

        if (orient == Orient.BOTTOM) drawBottom(root);
        else if (orient == Orient.TOP) drawTop(root);
        else if (orient == Orient.LEFT) drawLeft(root);
        else if (orient == Orient.RIGHT) drawRight(root);
        else if (orient == Orient.CUSTOM) drawCustom(root);

        result.put("root", root);

        return (Object)result;
    }

    public double get(double x) {
        if (options.has("key")) {
            String key = options.getString("key");
            x = chart.dataDouble((int)x, key);
        }

        return scale.get(x);
    }

    public double get(String x) {
        return scale.get(x);
    }

    protected void drawCustom(Transform root) {

    }

    protected void drawRight(Transform root) {

    }

    protected void drawLeft(Transform root) {

    }

    protected void drawTop(Transform root) {

    }

    protected void drawBottom(Transform root) {

    }

    protected String getFormatString(Object o) {
        return o.toString();
    }


    /** scale method alias */

    public double max() { return scale.max(); }

    public double min() { return scale.min(); }

    public double rangeBand() { return scale.rangeBand(); }

    public double rate(double value, double max) {
        return get(max() * (value / max));
    }

    public double invert(double y) {
        return scale.invert(y);
    }

    public JSONArray domain() {
        return scale.domain();
    }

    public JSONArray range() {
        return scale.range();
    }

    public JSONObject get(int i, double value) {
        return new JSONObject().put("x", i).put("y", value);
    }
}