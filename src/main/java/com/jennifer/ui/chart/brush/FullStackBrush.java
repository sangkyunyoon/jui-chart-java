package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class FullStackBrush extends Brush {

    private Transform root;
    private int outerPadding;
    private Grid x;
    private Grid y;
    private double zeroY;
    private int count;
    private double width;
    private double barWidth;
    private boolean hasText;

    public FullStackBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public FullStackBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {

        root = el("g").translate(chart.x(), chart.y());

        outerPadding = options.optInt("outerPadding", 15);

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        zeroY = y.get(0);
        count = chart.data().length();

        width = x.rangeBand();
        barWidth = width - outerPadding * 2;

        hasText = options.optBoolean("text", false);

    }

    @Override
    public Object draw() {
        int chart_height = chart.height();
        JSONArray target = options.array("target");

        for (int i = 0; i < count; i++) {
            double startX = x.get(i) - barWidth / 2;
            double sum = 0;
            OptionArray list = new OptionArray();

            for (int j = 0, jLen = target.length(); j < jLen; j++) {
                double height = chart.dataDouble(i, target.getString(j));

                sum += height;
                list.put(height);
            }

            double startY = 0, max = y.max(), current = max;

            for (int j = list.length() - 1; j >= 0; j--) {
                double height = chart_height - y.rate(list.getDouble(j) , sum);

                root.rect(opt()
                        .x(startX)
                        .y(startY)
                        .width(barWidth)
                        .height(height)
                        .fill(color(j))
                );

                if (hasText) {
                    double percent = Math.round((list.getDouble(j)/sum)*max);
                    root.text(opt().x(startX + barWidth/2).y(startY + height/2 + 8).textAnchor("middle")).textNode(((current - percent < 0 ) ? current+"" : percent) + "%");

                    current -= percent;
                }

                startY += height;
            }
        }

        return opt().put("root", root);
    }
}
