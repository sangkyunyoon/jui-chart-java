package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.scale.OrdinalScale;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * new BlockGrid().title().domain().target()
 *
 * Created by Jayden on 2014-10-24.
 */
public class BlockGrid extends Grid {

    private JSONObject attrs = new JSONObject();


    private JSONArray points;
    private JSONArray domain;
    private double band;
    private double half_band;
    private int bar;
    private int step;

    public BlockGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
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
            Option o = new Option()
                            .x2(chart.width());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.getString(i);

            if ("".equals(domain)) {
                continue;
            }

            Transform axis = root.group().translate(this.points.getDouble(i), 0);

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
            JSONObject o = new JSONObject();
            o.put("x2", chart.width());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.getString(i);

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
            Transform axis = root.group().translate(chart.width(), 0);

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("y2", hasLine ? -full_height : this.bar);

            axis.append(this.line(lineOpt));
        }
    }

    protected void drawLeft(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.getString(i);

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
            Transform axis = root.group().translate(0, chart.height());

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2", hasLine ? full_width : -this.bar);

            axis.append(this.line(lineOpt));
        }
    }


    protected void drawRight(Transform root) {
        int full_width = chart.width();

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.points.length(); i < len; i++) {

            //TODO: support format string
            String domain = this.domain.getString(i);

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
            Transform axis = root.group().translate(0, chart.height());

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

        int width = chart.width();
        int height = chart.height();
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

    private void initDomain() {

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
                domain.put(data.getJSONObject(i).getString(options.getString("target")));
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
