package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import com.jennifer.ui.util.dom.Polygon;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class ScatterBrush extends Brush {

    private Transform root;
    private String symbol;
    private int size;
    private Option target;

    public ScatterBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public ScatterBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());

        symbol = options.optString("symbol", "circle");
        size = options.optInt("size", 7);
    }

    @Override
    public Object draw() {

        drawScatter(getXY());

        return opt().put("root", root);
    }

    private void drawScatter(OptionArray points) {
        for(int i = 0; i < points.length(); i++) {
            Option point = (Option) points.object(i);
            for(int j = 0; j < point.array("x").length(); j++) {
                Transform p = this.createScatter(opt()
                        .x(point.array("x").getDouble(j))
                        .y(point.array("y").getDouble(j))
                        , i);
                root.append(p);
            }
        }
    }

    private Transform createScatter(Option pos, int i) {

        target = chart.series(options.array("target").getString(i));
        String s = (!target.has("symbol")) ? symbol : target.getString("symbol");
        double h = size ;
        double w = h ;

        String color = color(i);
        String borderColor = chart.theme("scatterBorderColor");
        double borderWidth = chart.themeDouble("scatterBorderWidth");

        Transform  g = null;

        if("triangle".equals(s) || "cross".equals(s)) {
            g = el("g", opt().width(w).height(h)).translate(pos.x() - (w / 2), pos.y() - (h / 2));

            if ("triangle".equals(s)) {
                Polygon poly = g.polygon();

                poly.point(0, h)
                        .point(w, h)
                        .point(w / 2, 0);
            } else {
                g.line(opt().stroke(color).strokeWidth(borderWidth * 2).x1(0).y1(0).x2(w).y2(h));
                g.line(opt().stroke(color).strokeWidth(borderWidth * 2).x1(0).y1(w).x2(h).y2(0));
            }
        } else  {
            if("rectangle".equals(s)) {
                g = el("rect", opt().width(w).height(h).x(pos.x()-(w/2)).y(pos.y()-(h/2)));
            } else {
                g = el("ellipse", opt().rx(w/2).ry(h/2).cx(pos.x()).cy(pos.y()));
            }
        }

        if (!"cross".equals(s)) {
            g.attr(opt().fill(color).stroke(borderColor).strokeWidth(borderWidth));
        }

        return g;
    }
}
