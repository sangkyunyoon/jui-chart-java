package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;

/**
 * Created by Jayden on 2014-10-27.
 */
public class BarBrush extends Brush {
    private Transform root;
    private int outerPadding;
    private int innerPadding;
    private Grid x;
    private Grid y;
    private double zeroX;
    private int count;
    private double height;
    private double half_height;
    private JSONArray target;
    private double barHeight;

    public BarBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {

        root = el("g").translate(chart.x(), chart.y());

        outerPadding = options.optInt("outerPadding", 2);
        innerPadding = options.optInt("innerPadding", 1);

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        zeroX = x.get(0);
        count = chart.data().length();

        target = options.getJSONArray("target");

        height = y.rangeBand();
        half_height = height - outerPadding*2;
        barHeight = (half_height - (target.length()-1) * innerPadding) / target.length();

    }

    @Override
    public Object draw() {

        for(int i = 0; i < count; i++) {
            double startY = y.get(i) - half_height/2;

            Transform group = root.group();

            for(int j = 0, len = target.length(); j < len; j++) {
                double startX = x.get(chart.dataDouble(i, target.getString(j)));
                double w = Math.abs(zeroX - startX);

                Option o = new Option().y(startY).height(barHeight).width(w).fill(color(j));
                if (startX >= zeroX) {
                    o.x(zeroX);
                } else {
                    o.x(zeroX - w);
                }

                group.rect(o);

                startY = startY + (barHeight + innerPadding);

            }
        }

        return new JSONObject().put("root", root);
    }
}
