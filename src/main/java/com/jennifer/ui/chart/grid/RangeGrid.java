package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.dom.Transform;
import com.jennifer.ui.util.scale.LinearScale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-24.
 */
public class RangeGrid extends Grid {
    private int step;
    private boolean nice;
    private JSONArray ticks;
    private int bar;

    private JSONArray values;

    public RangeGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }

    public void init() {
        scale = new LinearScale();
   }

    protected void drawTop(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.optBoolean("line", false);
        boolean hasFull = options.optBoolean("full", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("x2", chart.width());
            root.append(this.axisLine(o));
        }

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0 && ticks.getDouble(i) != min);
            Transform axis = root.group().translate(this.values.getDouble(i), 0);

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("y2",hasLine ? full_height : -bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            JSONObject textOpt = new JSONObject();
            textOpt.put("x", 0);
            textOpt.put("y", -bar - 4);
            textOpt.put("text-anchor", "middle");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }


    }

    public void drawBefore() {
        initDomain();

        int width = chart.width(), height = chart.height();
        scale.domain(options.getJSONArray("domain"));

        if (orient == Orient.LEFT || orient == Orient.RIGHT) {
            this.scale.range(new JSONArray().put(height).put(0));
        } else {
            this.scale.range(new JSONArray().put(0).put(width));
        }

        this.step = options.optInt("step", 10);
        this.nice = options.optBoolean("nice", false);
        this.ticks = ((LinearScale)scale).ticks(this.step, this.nice, 100000);
        this.bar = 6;

        this.values = new JSONArray();

        for (int i = 0, len = ticks.length(); i < len; i++) {
            this.values.put(this.scale.get(this.ticks.getDouble(i)));
        }

    }

    private void initDomain() {

        if (has("target") && !has("domain")) {

            if (options.get("target") instanceof String) {
                JSONArray list = new JSONArray();
                list.put(options.getString("target"));
                options.put("target", list);
            }


            double max = options.optDouble("max", 0);
            double min = options.optDouble("min", 0);

            JSONArray target = options.getJSONArray("target");
            JSONArray data = chart.data();
            JSONArray domain = new JSONArray();

            for (int i = 0, len = target.length(); i < len; i++) {
                String key = target.getString(i);

                double _max = chart.series(key).getDouble("max");
                double _min = chart.series(key).getDouble("min");
                if (max < _max)
                    max = _max;
                if (min > _min)
                    min = _min;
            }

            options.put("max", max);
            options.put("min", min);
            options.put("step", options.optInt("step", 10));

            double unit = options.optDouble("unit", Math.ceil((max - min) / options.getDouble("step")));

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
}
