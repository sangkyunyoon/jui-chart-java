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

package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.ChartDateFormat;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.TimeUtil;
import com.jennifer.ui.util.dom.Transform;
import com.jennifer.ui.util.scale.TimeScale;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;



/**
 * Created by Jayden on 2014-10-24.
 */
public class DateGrid extends Grid {
    private JSONArray ticks;
    private int bar;
    private JSONArray values;

    public DateGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }

    @Override
    public void init() {
        scale = new TimeScale();
    }

    protected void drawTop(Transform root) {
        int full_height = chart.area("height");

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            JSONObject o = new JSONObject().put("x2", chart.area("width"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = this.ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(this.values.getDouble(i), 0);

            JSONObject lineOpt = new JSONObject().put("y2", hasLine ? full_height : -bar);

            axis.append(line(lineOpt));

            JSONObject textOpt = new JSONObject().put("x", 0).put("y", -bar - 4).put("text-anchor", "middle").put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }

    }

    protected void drawBottom(Transform root) {
        int full_height = chart.area("height");

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("x2", chart.area("width"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(values.getDouble(i), 0);

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("y2", hasLine ? -full_height : bar);

            axis.append(line(lineOpt));

            JSONObject textOpt = new JSONObject();
            textOpt.put("x", 0);
            textOpt.put("y", bar * 3);
            textOpt.put("text-anchor", "middle");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }
    }

    protected void drawLeft(Transform root) {
        int full_width = chart.area("width");

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            JSONObject o = new JSONObject();
            o.put("y2", chart.area("height"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(0, values.getDouble(i));

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2",hasLine ? full_width : -bar);

            axis.append(line(lineOpt));

            JSONObject textOpt = new JSONObject();
            textOpt.put("x", -bar-4);
            textOpt.put("y", bar);
            textOpt.put("text-anchor", "end");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }
    }

    protected void drawRight(Transform root) {
        int full_width = chart.area("width");

        boolean hasLine = options.optBoolean("line", false);

        if (!hasLine) {
            JSONObject o = new JSONObject().put("y2", chart.area("height"));
            root.append(this.axisLine(o));
        }

        for (int i = 0, len = ticks.length(); i < len; i++) {

            Transform axis = root.group().translate(0, values.getDouble(i));

            JSONObject lineOpt = new JSONObject();
            lineOpt.put("x2",hasLine ? -full_width : bar);

            axis.append(line(lineOpt));

            JSONObject textOpt = new JSONObject();
            textOpt.put("x", bar+4);
            textOpt.put("y", -bar);
            textOpt.put("text-anchor", "start");
            textOpt.put("fill", chart.theme("gridFontColor"));

            axis.append(chart.text(textOpt, getFormatString(ticks.getLong(i))));
        }
    }

    private String getFormatString(long d) {
        String text;
        if (options.has("format")) {
            if (options.get("format") instanceof ChartDateFormat) {
                text = ((ChartDateFormat)options.get("format")).format(d);
            } else {
                text = TimeUtil.format(d, options.getString("format"));
            }

        } else {
            text = TimeUtil.format(d, options.getJSONArray("step"));
        }
        return text;
    }

    public void drawBefore() {
        initDomain();

        int max = chart.area("height");

        if (orient == Orient.TOP || orient == Orient.BOTTOM) {
            max = chart.area("width");
        }

        JSONArray range = (JSONArray)new JSONArray().put(0).put(max);

        TimeScale timeScale = (TimeScale) this.scale;

        timeScale.domain(options.getJSONArray("domain")).rangeRound(range);

        boolean realtime = options.optBoolean("realtime", false);
        JSONArray step = JSONUtil.clone(options.getJSONArray("step"));

        if (realtime) {
            this.ticks = timeScale.realTicks(step.getString(0), step.getInt(1));
        } else {
            this.ticks = timeScale.ticks(step.getString(0), step.getInt(1));
        }

        // step = [this.time.days, 1];
        this.bar = 6;

        this.values = new JSONArray();

        for (int i = 0, len = this.ticks.length(); i < len; i++) {
            this.values.put(this.scale.get(this.ticks.getDouble(i)));
        }
    }

    private void initDomain() {


        if (has("target") && !has("domain")) {

            if (options.get("target") instanceof String) {
                JSONArray list = new JSONArray();
                list.put(options.getString("target"));
                options.put("target", list);
            }

            JSONArray target = (JSONArray) options.getJSONArray("target");
            JSONArray domain = new JSONArray();
            JSONArray data = chart.data();

            long min = 0;
            long max = 0;

            boolean hasMin = options.has("min");
            boolean hasMax = options.has("max");

            for (int i = 0, len = target.length(); i < len; i++) {
                String key = target.getString(i);

                for(int index = 0, dataLength = data.length(); index < dataLength; index++) {
                    JSONObject row =  data.getJSONObject( index);

                    long value = 0;

                    if (row.get(key) instanceof Date) {
                        value = ((Date) row.get(key)).getTime();
                    } else {
                        value = row.getLong(key);
                    }

                    if (!hasMin) {
                        min = value;
                        hasMin = true;
                    }
                    else if (min > value) min = value;

                    if (!hasMax) {
                        max = value;
                        hasMax = true;
                    }
                    else if (max < value) max = value;
                }

            }

            options.put("max", max);
            options.put("min", min);

            domain.put(min).put(max);


            if (options.optBoolean("reverse", false)) {
                JSONUtil.reverse(domain);
            }

            options.put("domain", domain);

        }

    }

    public Object draw() {
        return this.drawGrid();
    }
}
