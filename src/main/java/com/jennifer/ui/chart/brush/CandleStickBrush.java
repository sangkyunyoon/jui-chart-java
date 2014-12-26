/*
 * Copyright (C) 2014 (JenniferSoft Inc.)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;

import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


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

    public CandleStickBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.area("x"), chart.area("y"));

        x = (Grid)options.get("x");
        y = (Grid)options.get("y");

        count = chart.data().length();
        width = x.rangeBand();
        barWidth = width * 0.7;
        barPadding = barWidth / 2;

    }

    @Override
    public Object draw() {

        JSONObject targets = getTargets();

        for(int i = 0; i < count; i++) {

            double startX = x.get(i);

            double open = targets.getJSONObject( "open").getJSONArray("data").getDouble(i);
            double close = targets.getJSONObject( "close").getJSONArray("data").getDouble(i);
            double low = targets.getJSONObject( "low").getJSONArray("data").getDouble(i);
            double high = targets.getJSONObject( "high").getJSONArray("data").getDouble(i);


            if(open > close) {
                double yValue = y.get(open);

                root.line(new JSONObject()
                        .put("x1",startX)
                        .put("y1",y.get(high))
                        .put("x2", startX)
                        .put("y2", y.get(low))
                        .put("stroke", chart.theme("candlestickInvertBorderColor"))
                        .put("stroke-width",1));

                root.rect(new JSONObject()
                        .put("x", startX - barPadding)
                        .put("y", yValue)
                        .put("width", barWidth)
                        .put("height", Math.abs(y.get(close) - yValue))
                        .put("fill",chart.theme("candlestickInvertBackgroundColor"))
                        .put("stroke", chart.theme("candlestickInvertBorderColor"))
                        .put("stroke-width",1));

            } else {
                double yValue = y.get(close);

                root.line(new JSONObject()
                        .put("x1",startX)
                        .put("y1",y.get(high))
                        .put("x2", startX)
                        .put("y2", y.get(low))
                        .put("stroke", chart.theme("candlestickBorderColor"))
                        .put("stroke-width",1));

                root.rect(new JSONObject()
                        .put("x", startX - barPadding)
                        .put("y", yValue)
                        .put("width", barWidth)
                        .put("height", Math.abs(y.get(open) - yValue))
                        .put("fill",chart.theme("candlestickBackgroundColor"))
                        .put("stroke", chart.theme("candlestickBorderColor"))
                        .put("stroke-width",1));

            }

        }

        return new JSONObject().put("root", root);
    }

    private JSONObject getTargets() {
        JSONObject result = new JSONObject();

        JSONArray target = options.getJSONArray("target");

        for(int i = 0, len = target.length(); i < len; i++) {
            JSONObject t = chart.series(target.getString(i));
            t.put(t.getString("type"), t);
        }

        return result;
    }
}
