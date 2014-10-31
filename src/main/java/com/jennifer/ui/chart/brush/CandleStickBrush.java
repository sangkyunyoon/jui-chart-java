package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class CandleStickBrush extends Brush {
    private Transform root;
    private Grid x;
    private Grid y;
    private int count;
    private double width;
    private double barWidth;
    private double barPadding;

    public CandleStickBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }
    public CandleStickBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        count = chart.data().length();
        width = x.rangeBand();
        barWidth = width * 0.7;
        barPadding = barWidth / 2;

    }

    @Override
    public Object draw() {

        Option targets = getTargets();

        for(int i = 0; i < count; i++) {

            double startX = x.get(i);

            double open = targets.object("open").getJSONArray("data").getDouble(i);
            double close = targets.object("close").getJSONArray("data").getDouble(i);
            double low = targets.object("low").getJSONArray("data").getDouble(i);
            double high = targets.object("high").getJSONArray("data").getDouble(i);


            if(open > close) {
                double yValue = y.get(open);

                root.line(opt()
                        .x1(startX)
                        .y1(y.get(high))
                        .x2(startX)
                        .y2(y.get(low))
                        .stroke(chart.theme("candlestickInvertBorderColor"))
                        .strokeWidth(1));

                root.rect(opt()
                        .x(startX - barPadding)
                        .y(yValue)
                        .width(barWidth)
                        .height(Math.abs(y.get(close) - yValue))
                        .fill(chart.theme("candlestickInvertBackgroundColor"))
                        .stroke(chart.theme("candlestickInvertBorderColor"))
                        .strokeWidth(1));

            } else {
                double yValue = y.get(close);

                root.line(opt()
                        .x1(startX)
                        .y1(y.get(high))
                        .x2(startX)
                        .y2(y.get(low))
                        .stroke(chart.theme("candlestickBorderColor"))
                        .strokeWidth(1));

                root.rect(opt()
                        .x(startX - barPadding)
                        .y(yValue)
                        .width(barWidth)
                        .height(Math.abs(y.get(open) - yValue))
                        .fill(chart.theme("candlestickBackgroundColor"))
                        .stroke(chart.theme("candlestickBorderColor"))
                        .strokeWidth(1));

            }

        }

        return new Option().put("root", root);
    }

    private Option getTargets() {
        Option result = new Option();

        JSONArray target = options.getJSONArray("target");

        for(int i = 0, len = target.length(); i < len; i++) {
            JSONObject t = chart.series(target.getString(i));
            t.put(t.getString("type"), t);
        }

        return result;
    }
}
