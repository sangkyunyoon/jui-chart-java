package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Polygon;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class ScatterPathBrush extends Brush {


    private Transform root;
    private String symbol;
    private double size;

    public ScatterPathBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public ScatterPathBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());

        symbol = options.optString("symbol", "circle");
        size = options.optDouble("size", 7);
    }

    @Override
    public Object draw() {

        drawScatter(getXY());

        return opt().put("root", root);
    }

    private void drawScatter(OptionArray points) {
        double height = size;
        double width = height;

        Path path = root.path(opt()
                .fill(color(0))
                .stroke(color(0))
                .strokeWidth(chart.theme("scatterBorderWidth")));

        JSONArray t = options.array("target");

        for(int i = 0; i < points.length(); i++) {
            Option target = chart.series(t.getString(i));
            String s = (!target.has("symbol")) ? symbol : target.getString("symbol");

            Option point = (Option) points.object(i);
            for(int j = 0; j < point.array("x").length(); j++) {

                if ("circle".equals(s)) {
                    path.circle(point.array("x").getDouble(j), point.array("y").getDouble(j), width/2);
                } else if ("rect".equals(s) || "rectangle".equals(s)) {
                    path.rect(point.array("x").getDouble(j), point.array("y").getDouble(j), width, height);
                } else if ("triangle".equals(s)) {
                    path.triangle(point.array("x").getDouble(j), point.array("y").getDouble(j), width, height);
                } else if ("cross".equals(s)) {
                    path.cross(point.array("x").getDouble(j), point.array("y").getDouble(j), width, height);
                }


            }
        }
    }
}
