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

import com.jennifer.ui.chart.AbstractDraw;
import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.dom.Path;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public abstract class Brush extends AbstractDraw {

    public static final String SYMBOL_CURVE = "curve";
    public static final String SYMBOL_STEP = "step";

    protected ChartBuilder chart;
    protected JSONObject options;

    public Brush(ChartBuilder chart, JSONObject options) {
        this.chart = chart;
        this.options = options;
    }

    public JSONObject curvePoints(JSONArray K) {
        JSONObject result = new JSONObject();
        JSONArray p1 = new JSONArray();
        JSONArray p2 = new JSONArray();

        int n = K.length() - 1;

        /* rhs vector*/
        JSONArray a = new JSONArray();
        JSONArray b = new JSONArray();
        JSONArray c = new JSONArray();
        JSONArray r = new JSONArray();

        /*left most segment */
        a.put(0, 0);
        b.put(0, 2);
        c.put(0, 1);
        r.put(0, K.getDouble(0) + 2 * K.getDouble(1));

        /* internal segments */
        for(int  i = 1; i < n - 1; i++) {
            a.put(i, 1);
            b.put(i, 4);
            c.put(i, 1);
            r.put(i, 4* K.getDouble(i) + 2 * K.getDouble(i + 1));
        }

        /*right segment*/
        a.put(n-1, 2);
        b.put(n-1, 7);
        c.put(n-1, 0);
        r.put(n-1, 8*K.getDouble(n-1) + K.getDouble(n));

        /*solves Ax=b with the Thomas algorithm (from Wikipedia)*/
        for (int i = 1; i < n; i++) {
            double m = a.getDouble(i) / b.getDouble(i - 1);
            b.put(i, b.getDouble(i) - m * c.getDouble(i-1));
            r.put(i, r.getDouble(i) - m * r.getDouble(i-1));
        }


        p1.put(n - 1, r.getDouble(n - 1) / b.getDouble(n - 1));

        for (int i = n - 2; i >= 0; --i) {
            p1.put(i, (r.getDouble(i) - c.getDouble(i) * p1.getDouble(i + 1)) / b.getDouble(i));
        }

		/*we have p1, now compute p2*/
        for (int i = 0; i < n - 1; i++) {
            p2.put(i , 2 * K.getDouble(i + 1) - p1.getDouble(i + 1));
        }


        p2.put(n - 1, 0.5 * (K.getDouble(n) + p1.getDouble(n - 1)));

        result.put("p1", p1);
        result.put("p2", p2);

        return result;
    }


    public double getScaleValue(double value, double minValue, double maxValue, double minRadius, double maxRadius) {
        double range = maxRadius - minRadius;
        double per = (value - minValue) / (maxValue - minValue);

        return range * per + minRadius;
    }

    public JSONArray getXY () {
        JSONArray data = chart.data();

        Grid x = (Grid)this.options.get("x");
        Grid y = (Grid)this.options.get("y");

        JSONArray xy = new JSONArray();
        JSONArray target = JSONUtil.clone(options.getJSONArray( "target"));

        for(int i = 0, len = data.length(); i < len; i++) {
            double startX = x.get(i);
            JSONObject obj = data.getJSONObject( i);

            for (int j = 0, jLen = target.length(); j < jLen; j++) {
                String key = target.getString(j);
                double value = obj.getDouble(key);
                JSONObject series = chart.series(key);

                if (xy.isNull(j)) {

                    JSONObject o = new JSONObject();
                    o.put("x", new JSONArray());
                    o.put("y", new JSONArray());
                    o.put("value", new JSONArray());
                    o.put("min", new JSONArray());
                    o.put("max", new JSONArray());
                    xy.put(j, o);
                }

                JSONObject axis = xy.getJSONObject( j);

                axis.getJSONArray( "x").put(startX);
                axis.getJSONArray( "y").put(y.get(value));
                axis.getJSONArray( "value").put(value);
                axis.getJSONArray( "min").put(value == series.getDouble("min"));
                axis.getJSONArray( "max").put(value == series.getDouble("max"));
            }
        }


        return xy;
    }

    public JSONArray getStackXY() {
        JSONArray xy = getXY();

        Grid x = (Grid)this.options.get("x");
        Grid y = (Grid)this.options.get("y");

        JSONArray data = chart.data();
        JSONArray target = (JSONArray)options.getJSONArray( "target");

        for(int i = 0, len = data.length(); i < len; i++) {
            JSONObject obj = data.getJSONObject( i);
            double valueSum = 0;

            for (int j = 0, jLen = target.length(); j < jLen; j++) {
                String key = target.getString(j);
                double value = obj.getDouble(key);

                if(j > 0) {
                    valueSum += obj.getDouble(target.getString(j - 1));
                }

                (xy.getJSONObject( j)).getJSONArray( "y").put(i, y.get(value + valueSum));
            }
        }

        return xy;
    }

    public String color(int i) {
        return chart.color(i, options.getJSONArray( "colors"));
    }

    /**
     * static create method
     *
     * @param type
     * @return
     */
    public static JSONObject create(String type) { return new JSONObject().put("type", type); }
    public static JSONObject bar() { return create("bar"); }
    public static JSONObject area() { return create("area"); }
    public static JSONObject column() { return create("column"); }
    public static JSONObject stackbar() { return create("stackbar"); }

    public static JSONObject stackcolumn() { return create("stackcolumn"); }
}
