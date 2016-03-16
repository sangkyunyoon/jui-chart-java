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
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;

public class AreaBrush extends LineBrush {

    private Transform root;
    private int maxY;

    public AreaBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.area("x"), chart.area("y"));
        maxY = chart.area("height");
    }

    @Override
    public Object draw() {
        return drawArea(this.getXY());
    }

    protected Object drawArea(JSONArray path) {
        for(int k = 0, len = path.length(); k < len; k++) {
            JSONObject o =  path.getJSONObject(k);

            DomUtil g = createLine(o, k);
            Path p = (Path)g.children(0);
            JSONArray xList = o.getJSONArray("x");

            p.LineTo(xList.getDouble(xList.length() - 1), maxY);
            p.LineTo(xList.getDouble(0), maxY);
            p.Close();

            p.put("fill", color(k));
            p.put("fill-opacity", chart.theme("areaOpacity"));
            p.put("stroke-width", 0);


            root.append(p);

        }

        return new JSONObject().put("root", root);
    }


}
