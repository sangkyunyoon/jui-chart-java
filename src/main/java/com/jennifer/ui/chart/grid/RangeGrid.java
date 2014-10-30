package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.dom.Transform;
import com.jennifer.ui.util.scale.LinearScale;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by Jayden on 2014-10-24.
 */
public class RangeGrid extends Grid {
    protected int step;
    protected boolean nice;
    protected JSONArray ticks;
    protected int bar;

    protected JSONArray values;
    protected String DEFAULT_FORMAT = "###";  // DecimalFormat

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
            textOpt.put("fill", chart.theme(isZero, "gridActiveFontColor", "gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
        }
    }

    @Override
    protected void drawBottom(Transform root) {
        int full_height = chart.height();

        boolean hasLine = options.optBoolean("line", false);

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
            lineOpt.put("y2",hasLine ? -full_height : bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            JSONObject textOpt = new JSONObject();
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
            JSONObject o = new JSONObject();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0 && ticks.getDouble(i) != min);

            Transform axis = root.group().translate(0, values.getDouble(i));

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2",hasLine ? full_width : -bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!options.optBoolean("hideText", false)) {

                JSONObject textOpt = new JSONObject();
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
            JSONObject o = new JSONObject();
            o.put("y2", chart.height());
            root.append(this.axisLine(o));
        }

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0 && ticks.getDouble(i) != min);

            Transform axis = root.group().translate(0, values.getDouble(i));

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2",hasLine ? -full_width : bar);
            lineOpt.put("stroke", chart.theme(isZero, "gridActiveBorderColor", "gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme(isZero, "gridActiveBorderWidth", "gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!options.optBoolean("hideText", false)) {

                JSONObject textOpt = new JSONObject();
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
        scale.domain(options.getJSONArray("domain"));

        if (orient == Orient.LEFT || orient == Orient.RIGHT) {
            this.scale.range(new JSONArray().put(height).put(0));
        } else {
            this.scale.range(new JSONArray().put(0).put(width));
        }

        this.step = options.optInt("step", 10);
        this.nice = options.optBoolean("nice", false);
        this.ticks = ((LinearScale)scale).ticks(this.step, this.nice);
        this.bar = 6;

        this.values = new JSONArray();

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
            JSONObject series = chart.series();

            for (int i = 0, len = target.length(); i < len; i++) {
                String key = target.getString(i);

                if(series.has(key)) {
                    double _max = series.getJSONObject(key).getDouble("max");
                    double _min = series.getJSONObject(key).getDouble("min");
                    if (max < _max)
                        max = _max;
                    if (min > _min)
                        min = _min;
                }

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

    @Override
    protected String getFormatString(Object value) {

        DecimalFormat df = new DecimalFormat(options.optString("format", DEFAULT_FORMAT));

        return df.format(Double.valueOf(value.toString()));
    }

}
