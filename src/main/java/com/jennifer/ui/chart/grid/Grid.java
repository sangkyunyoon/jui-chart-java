package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.AbstractDraw;
import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.scale.Scale;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-24.
 */
public abstract class Grid extends AbstractDraw {

    protected Scale scale;
    protected Orient orient;
    protected ChartBuilder chart;
    protected Option options;

    public Grid(Orient orient, ChartBuilder chart, Option options) {
        this.chart = chart;
        this.orient = orient;
        this.options = options;

        init();
    }

    public Grid(Orient orient, ChartBuilder chart, JSONObject options) {
        this(orient, chart, JSONUtil.extend(options));
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

    protected Transform axisLine(Option attr) {

        Option o = opt();
        o.put("x1", 0);
        o.put("y1", 0);
        o.put("x2", 0);
        o.put("y2", 0);
        o.put("stroke", chart.theme("gridAxisBorderColor"));
        o.put("stroke-width", chart.theme("gridAxisBorderWidth"));
        o.put("stroke-opacity", 1);

        return el("line", JSONUtil.extend(o, attr));
    }

    protected Transform line(Option attr) {
        Option o = new Option();
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
        Option result = new Option();

        Option o = new Option();
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

    public Option get(int i, double value) {
        return (Option)opt().put("x", i).put("y", value);
    }
}