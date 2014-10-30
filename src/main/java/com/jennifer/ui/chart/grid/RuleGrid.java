package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.dom.Transform;
import com.jennifer.ui.util.scale.LinearScale;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.font.NumericShaper;
import java.text.DecimalFormat;

/**
 * Created by Jayden on 2014-10-24.
 */
public class RuleGrid extends RangeGrid {

    private boolean hideZero;
    private boolean center;

    public RuleGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }


    @Override
    protected void drawTop(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? half_height : 0;

        JSONObject o = new JSONObject();
        o.put("y1", centerPosition);
        o.put("y2", centerPosition);
        o.put("x2", chart.width());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(this.values.getDouble(i), centerPosition );

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("y1",center ? -bar : 0);
            lineOpt.put("y2",bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                JSONObject textOpt = new JSONObject();
                textOpt.put("x", 0);
                textOpt.put("y", bar*2 + 4);
                textOpt.put("text-anchor", "middle");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }
    }

    @Override
    protected void drawBottom(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? -half_height : 0;

        JSONObject o = new JSONObject();
        o.put("y1", centerPosition);
        o.put("y2", centerPosition);
        o.put("x2", chart.width());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(this.values.getDouble(i), centerPosition );

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("y1",center ? -bar : 0);
            lineOpt.put("y2",center ? bar : -bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                JSONObject textOpt = new JSONObject();
                textOpt.put("x", 0);
                textOpt.put("y", -bar*2);
                textOpt.put("text-anchor", "middle");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }
    }

    @Override
    protected void drawLeft(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? half_width : 0;

        JSONObject o = new JSONObject();
        o.put("x1", centerPosition);
        o.put("x2", centerPosition);
        o.put("y2", chart.height());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(centerPosition, this.values.getDouble(i));

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x1",center ? -bar : 0);
            lineOpt.put("x2",bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                JSONObject textOpt = new JSONObject();
                textOpt.put("x", 2*bar);
                textOpt.put("y", bar-2);
                textOpt.put("text-anchor", "start");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }
    }


    @Override
    protected void drawRight(Transform root) {
        int width = chart.width();
        int height = chart.height();
        double half_width = (double)width/2;
        double half_height = (double)height/2;

        double centerPosition = center ? -half_width : 0;

        JSONObject o = new JSONObject();
        o.put("x1", centerPosition);
        o.put("x2", centerPosition);
        o.put("y2", chart.width());
        root.append(this.axisLine(o));

        double min = this.scale.min();


        for (int i = 0, len = ticks.length(); i < len; i++) {
            boolean isZero = (ticks.getDouble(i) == 0);

            Transform axis = root.group().translate(centerPosition, this.values.getDouble(i));

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x1",center ? -bar : 0);
            lineOpt.put("x2",center ? bar : -bar);
            lineOpt.put("stroke", chart.theme("gridAxisBorderColor"));
            lineOpt.put("stroke-width", chart.theme("gridBorderWidth"));

            axis.append(line(lineOpt));

            if (!isZero || (isZero && !hideZero)) {
                JSONObject textOpt = new JSONObject();
                textOpt.put("x", -bar - 4 );
                textOpt.put("y", bar-2);
                textOpt.put("text-anchor", "middle");
                textOpt.put("fill", chart.theme("gridFontColor"));

                axis.append(chart.text(textOpt, getFormatString(ticks.get(i))));
            }

        }

    }

    public void drawBefore() {
        super.drawBefore();

        this.hideZero = options.optBoolean("hideZero", false);
        this.center = options.optBoolean("center", false);

    }

}
