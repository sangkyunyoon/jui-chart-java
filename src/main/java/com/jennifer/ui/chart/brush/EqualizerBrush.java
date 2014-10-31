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
public class EqualizerBrush extends Brush {
    private Transform root;
    private int innerPadding;
    private int outerPadding;
    private int unit;
    private int gap;
    private Grid x;
    private Grid y;
    private double zeroY;
    private int count;
    private double width;
    private JSONArray target;
    private double half_width;
    private double barWidth;

    public EqualizerBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public EqualizerBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());

        innerPadding = options.optInt("innerPadding", 5);
        outerPadding = options.optInt("outerPadding", 15);
        unit = options.optInt("unit", 5);
        gap = options.optInt("gap", 5);

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        zeroY = y.get(0);
        count = chart.data().length();

        width = x.rangeBand();
        target = options.array("target");
        half_width = (width - outerPadding * 2) / 2;
        barWidth = (width - outerPadding * 2 - (target.length() - 1) * innerPadding) / target.length();

    }

    @Override
    public Object draw() {
        for (int i = 0; i < count; i++) {
            double startX = x.get(i) - half_width;

            for (int j = 0, jLen = target.length(); j < jLen; j++) {
                Transform barGroup = root.group();

                double startY = y.get(chart.dataDouble(i, target.getString(j)));
                double padding = 1.5;
                double eY = zeroY;
                int eIndex = 0;

                 Option o = opt()
                        .x(startX)
                        .width(barWidth)
                        .fill(color((int) Math.floor(eIndex / gap)));

                if (startY <= zeroY) {
                    while (eY > startY) {
                        double unitHeight = (eY - unit < startY) ? Math.abs(eY - startY) : unit;

                        o.y(eY - unitHeight).height(unitHeight);

                        eY -= unitHeight + padding;
                        eIndex++;
                    }
                } else {
                    while (eY < startY) {
                        double unitHeight = (eY + unit > startY) ? Math.abs(eY - startY) : unit;

                        o.y(eY).height(unitHeight);

                        eY += unitHeight + padding;
                        eIndex++;

                    }
                }

                barGroup.rect(o);

                startX += barWidth + innerPadding;
            }
        }

        return opt().put("root", root);
    }
}

