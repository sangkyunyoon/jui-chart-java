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
public class BarGaugeBrush extends Brush {
    private Transform root;
    private int max;
    private int x;
    private int y;
    private int count;
    private JSONArray target;
    private int cut;
    private int size;
    private String align;
    private boolean split;

    public BarGaugeBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }
    public BarGaugeBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }


    @Override
    public void drawBefore() {

        root = (Transform)el("g").translate(chart.x(), chart.y()).put("class", "brush bar gauge");

        max = chart.width();

        x = 0;
        y = 0;

        count = chart.data().length();

        size = options.optInt("size", 20);
        cut = options.optInt("cut", 5);

        align = options.optString("align", "left");
        split = options.optBoolean("split", false);

    }

    @Override
    public Object draw() {

        for(int i = 0; i < count; i++) {

            JSONObject data = chart.data(i);

            Transform group = root.group();

            String color = color(i);

            Option o = new Option()
                    .x(x)
                    .y(y + size/2 + cut)
                    .textAnchor("end")
                    .fill(color);

            String text = "";
            if (data.has(options.getString("title"))) {
                text = data.getString(options.getString("title"));
            } else {
                text = data.getString("title");
            }

            group.append(chart.text(o, text));

            Option rectOpt = new Option()
                    .x(x + cut)
                    .y(y)
                    .width(max)
                    .height(size)
                    .fill(chart.theme("gaugeBackgroundColor"));
            group.rect(rectOpt);

            double value = data.getDouble("value") * max / 100;
            double ex = (100 - data.getDouble("value")) * max / 100;
            double startX = x + cut;

            if ("center".equals(align)) {
                startX += (max/2 - value/2);
            } else if ("right".equals(align)) {
                startX += max - value;
            }

            Option rectOpt2 = new Option()
                    .x(startX)
                    .y(y)
                    .width(value)
                    .height(size)
                    .fill(color);

            group.rect(rectOpt2);

            double textX = 0;
            String textAlign = "";
            String textColor = color;

            if (split) {
                textX = x + value + cut * 2 + ex;
                textAlign = "start";
                textColor = color;
            } else {
                textX = x + cut * 2;
                textAlign = "start";
                textColor = "white";

                if ("center".equals(align)) {
                    textX = x + cut + max / 2;
                    textAlign = "middle";
                } else if ("right".equals(align)) {
                    textX = x + max;
                    textAlign = "end";
                }
            }

            Option textOpt = new Option()
                    .x(textX)
                    .y(y + size/2 + cut)
                    .textAnchor(textAlign)
                    .fill(textColor);

            group.append(chart.text(textOpt, data.getDouble("value") + "%"));

            y += size + cut;
        }

        return new JSONObject().put("root", root);
    }
}
