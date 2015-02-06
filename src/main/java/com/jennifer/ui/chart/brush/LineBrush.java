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
import com.jennifer.ui.util.DomUtil;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static com.jennifer.ui.util.DomUtil.el;

/**
 * Created by Jayden on 2014-10-27.
 */
public class LineBrush extends Brush {
    private Transform root;
    private String symbol;
    private boolean move;

    public LineBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {

        root = el("g").translate(chart.area("x"), chart.area("y"));
        symbol = options.optString("symbol", "normal");
        move = options.optBoolean("move", false);
    }



    @Override
    public Object draw() {
        return drawLine(getXY());
    }

    protected Object drawLine(JSONArray path) {
        for(int k = 0, len = path.length(); k < len; k++) {
            root.append(createLine( path.getJSONObject( k), k));
        }

        return new JSONObject().put("root", root);
    }

    protected DomUtil createLine(JSONObject pos, int index) {
        JSONArray x = JSONUtil.clone(pos.getJSONArray("x"));
        JSONArray y = JSONUtil.clone(pos.getJSONArray("y"));
        JSONArray valueList = JSONUtil.clone(pos.getJSONArray("value"));
        DecimalFormat format = new DecimalFormat(".##");
        String _color = color(index);
        Transform t = null;

        Transform g = new Transform("g");
        Path p = new Path(new JSONObject()
            .put("stroke", _color)
            .put("stroke-width",chart.theme("lineBorderWidth"))
            .put("fill","transparent")
        );

        p.MoveTo(x.getDouble(0), y.getDouble(0));

        if (Brush.SYMBOL_CURVE.equals(symbol)) {
            JSONObject px = this.curvePoints(x);
            JSONObject py = this.curvePoints(y);

            for (int i = 0, len = x.length(); i < len; i++) {
                p.CurveTo(
                        px.getJSONArray("p1").getDouble(i),
                        py.getJSONArray("p1").getDouble(i),
                        px.getJSONArray("p2").getDouble(i),
                        py.getJSONArray("p2").getDouble(i),
                        x.getDouble(i + 1),
                        y.getDouble(i + 1)
               );
            }
        } else {

            double maxValue = Double.MIN_VALUE;
            t = null;

            for (int i = 0, len = x.length(); i < len-1; i++) {
                if(Brush.SYMBOL_STEP.equals(symbol)) {
                    p.LineTo(x.getDouble(i), y.getDouble(i + 1));
                }

                double xValue = x.getDouble(i + 1);
                double yValue = y.getDouble(i + 1);
                double valueValue = valueList.getDouble( i + 1);

                p.LineTo(xValue, yValue);

                // display max value
                if (valueValue > maxValue) {
                    maxValue = valueValue;
                    t = createMaxElement(xValue, yValue, formatNumber(valueValue), _color );
                }

            }

        }

        // 라인 옆으로 옮기기
        if (move) {
            double range = (x.getDouble(2) - x.getDouble(1)) / 2;
            g.translate(range, 0);
        }

        g.append(p);
        g.append(t);

        return g;
    }
}
