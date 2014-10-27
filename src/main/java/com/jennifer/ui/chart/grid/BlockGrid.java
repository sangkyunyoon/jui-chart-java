package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.ChartGrid;
import com.jennifer.ui.common.ChartData;
import com.jennifer.ui.util.OrdinalScale;
import com.jennifer.ui.util.Scale;
import com.jennifer.ui.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 *
 * new BlockGrid().title().domain().target()
 *
 * Created by Jayden on 2014-10-24.
 */
public class BlockGrid extends Grid {

    private OrdinalScale scale = new OrdinalScale();
    private JSONObject attrs = new JSONObject();


    private double[] points;
    private double[] domain;
    private double band;
    private double half_band;
    private int bar;
    private int step;

    public BlockGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }

    @Override
    public void init() {

    }

    /*
    public Dom top(Dom g) {
        int full_height = chart.height();

        if (!this.has("line")) {
            g.append(this.axisLine(chart, {
                    x2 : chart.width()
            }))
        }

        for (int i = 0; i < this.points.length; i++) {

            String domain = (this.has("format")) ? this.execFormat(this.domain[i]) : this.domain[i];

            if ("".equals(domain)) {
                continue;
            }

            Dom axis = dom.group({
                    "transform" : "translate(" + this.points[i] + ", 0)"
            })

            axis.append(this.line(chart, {
                    x1 : -this.half_band,
                    y1 : 0,
                    x2 : -this.half_band,
                    y2 : (this.has("line")) ? full_height : this.bar
            }));

            axis.append(chart.text({
                    x : 0,
                    y : -20,
                    "text-anchor" : "middle"
            }, (grid.format) ? grid.format(this.domain[i]) : this.domain[i]))

            g.append(axis);
        }

        if (!this.has("full")) {
            var axis = dom.group({
                    "transform" : "translate(" + chart.width() + ", 0)"
            })

            axis.append(this.line(chart, {
                    y2 : (grid.line) ? full_height : this.bar
            }));

            g.append(axis);
        }

        return g;
    } */

    public boolean full() {
        return has("full") ? b("full") : false;
    }

    public boolean reverse() {
        return has("reverse") ? b("reverse") : false;
    }

    public void drawBefore() {
        initDomain();

        int width = chart.width();
        int height = chart.height();
        int max = (orient == Orient.LEFT || orient == Orient.RIGHT) ? height : width;

        this.scale.domain(options.getJSONArray("domain"));

        double range[] = new double[] { 0, (double)max };

        if (this.full()) {
            this.scale.rangeBands(range, 0, 0);
        } else {
            this.scale.rangePoints(range, 0);
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
                domain.put(data.getJSONObject(i).getDouble(options.getString("target")));
            }

            options.put("domain", domain);
            options.put("step", options.optInt("step", 10));
            options.put("max", options.optInt("max", 100));

        }

    }

    public Object draw() {
        return null;
        //return this.drawGrid();
    }
}
