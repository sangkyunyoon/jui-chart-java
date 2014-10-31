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
public class OhlcBrush extends Brush {

    private int count;
    private Transform root;
    private Grid x;
    private Grid y;

    public OhlcBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public OhlcBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
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

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());
        count = chart.data().length();

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");
    }

    @Override
    public Object draw() {
        Option targets = getTargets();
        double radius = chart.themeDouble("ohlcBorderRadius");
        for (int i = 0; i < count; i++) {
            double startX = x.get(i);

            double open = targets.object("open").getJSONArray("data").getDouble(i);
            double close = targets.object("close").getJSONArray("data").getDouble(i);
            double low = targets.object("low").getJSONArray("data").getDouble(i);
            double high = targets.object("high").getJSONArray("data").getDouble(i);

            String color = chart.theme((open > close), "ohlcInvertBorderColor", "ohlcBorderColor");

            // lowhigh
            root.line(opt()
                    .x1(startX)
                    .y1(y.get(high))
                    .x2(startX)
                    .y2(y.get(low))
                    .stroke(color)
                    .strokeWidth(1));

            // close
            root.line(opt()
                    .x1(startX)
                    .y1(y.get(close))
                    .x2(startX + radius)
                    .y2(y.get(close))
                    .stroke(color)
                    .strokeWidth(1));

            // open
            root.line(opt()
                    .x1(startX)
                    .y1(y.get(open))
                    .x2(startX - radius )
                    .y2(y.get(open))
                    .stroke(color)
                    .strokeWidth(1));
        }

        return opt().put("root", root);
    }
}
