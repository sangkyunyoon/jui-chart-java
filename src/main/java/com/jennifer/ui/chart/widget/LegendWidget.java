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

package com.jennifer.ui.chart.widget;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Orient;
import com.jennifer.ui.util.JSONUtil;


import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;


/**
 * Created by Jayden on 2014-11-03.
 */
public class LegendWidget extends Widget {
    private Transform root;
    private JSONArray brush;
    private String position;
    private String align;
    private String key;
    private double fontWidth;
    private double fontHeight;

    public LegendWidget(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g");

        brush = JSONUtil.clone(options.getJSONArray("brush"));

        if (brush.length() == 0) {
            brush.put(0);
        }

        position = options.optString("position", "bottom");
        align = options.optString("align", "center");
        key = options.getString("key");
        fontWidth = chart.themeDouble("legendFontSize");
        fontHeight = fontWidth;
    }

    @Override
    public Object draw() {

        double x = 0, y = 0, total_width = 0, total_height = 0, max_width = 0, max_height = 0;

        for (int i = 0, len = brush.length(); i < len; i++) {
            int index = brush.getInt(i);
            JSONObject brushObject = chart.brush(index);

            JSONArray arr = getLegendIcon(brushObject);

            for (int k = 0, kLen = arr.length(); k < kLen; k++) {
                JSONObject obj =  arr.getJSONObject( k);

                double w = obj.getDouble("width");
                double h = obj.getDouble("height");
                Transform icon = (Transform)obj.get("icon");

                //root.append(icon);
                icon.translate(x, y);

                if ("bottom".equals(position) || "top".equals(position)) {
                    x += w;
                    total_width += w;

                    if (max_height < h) {
                        max_height = h;
                    }
                } else {
                    y += h;
                    total_height += h;

                    if (max_width < w) {
                        max_width = w;
                    }
                }

            }
        }


        if ("bottom".equals(position) || "top".equals(position)) {
            y = ("bottom".equals(position)) ? chart.area("y2") + chart.padding("bottom") - max_height : chart.area("y") - chart.padding("top");

            if ("start".equals(align)) {
                x = chart.area("x");
            } else if ("center".equals(align)) {
                x = chart.area("x") + (chart.area("width") / 2.0 - total_width / 2.0);
            } else if ("end".equals(align)) {
                x = chart.area("x2") - total_width;
            }

        } else {
            x = ("left".equals(position)) ? chart.area("x") - chart.padding("left") : chart.area("x2") + chart.padding("right") - max_width;

            if ("start".equals(align)) {
                y = chart.area("y");
            } else if ("center".equals(align)) {
                y = chart.area("y") + (chart.area("height") / 2.0 - total_height / 2.0);
            } else if ("end".equals(align)) {
                y = chart.area("y2") - total_height;
            }

        }

        root.translate(x, y);

        return new JSONObject().put("root", root);
    }

    private JSONArray getLegendIcon(JSONObject brushObject) {
        JSONArray arr = new JSONArray();
        JSONArray data = JSONUtil.clone(brushObject.getJSONArray("target"));

        if (key != null && key.length() > 0) {
            data = chart.data();
        }

        int count = data.length();

        for (int i = 0; i < count; i++) {
            String text = "";

            if (key != null && key.length() > 0) {
                text = chart.series(key).optString("text",  data.getJSONObject(i).getString(key));
            } else {
                String target = data.getString(i);
                text = chart.series(target).optString("text", target);
            }

            double rectWidth = (fontWidth - 4) * text.length();
            double width = Math.min(rectWidth, fontHeight);
            double height = width;

            Transform group = root.group( new JSONObject().put("class", "legend icon"));

            Transform rect = group.rect(new JSONObject()
                    .put("x", 0)
                    .put("y", 0)
                    .put("width", width)
                    .put("height", height)
                    .put("fill", chart.color(i, brushObject.optJSONArray("colors")))
            );

            group.text(new JSONObject()
                .put("x", width + 4)
                .put("y", fontHeight - 3) // 3 is top, bottom font margin
                .put("font-family", chart.theme("fontFamily"))
                .put("font-size", chart.theme("legendFontSize"))
                .put("fill", chart.theme("legendFontColor"))
                .put("text-anchor", "start")
            ).textNode(text);

            arr.put(new JSONObject()
                .put("width", width + 4 + rectWidth + (i == count - 1 ? 0 : 10))
                .put("height", height + 4)
                .put("icon", group)

            );

        }

        return arr;
    }
}
