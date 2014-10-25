package com.jennifer.ui.chart;

import com.jennifer.ui.common.ChartData;
import com.jennifer.ui.util.ColorUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * ChartBuilder options list
 *
 * width : px
 * height : px
 * padding : 0
 * paddingLeft : 0
 * paddingRight : 0
 * paddingTop : 0
 * paddingBottom : 0
 * theme
 * series
 * brush
 * widget
 * data
 *
 *
 * Created by yuni on 2014-10-23.
 */
public class ChartBuilder extends AbstractDraw {

    private final JSONObject options;
    private final JSONObject builderOptions = new JSONObject();

    public ChartBuilder() {
        this(new JSONObject());
    }

    public ChartBuilder(JSONObject o) {
        this.options = o;

        caculate();
    }

    public int i(String key) {
        return this.options.getInt(key);
    }

    public int bi(String key) {
        return this.builderOptions.getInt(key);
    }

    private void caculate() {

        int width  = i("width") - (padding("left") + padding("right"));
        int height  = i("height") - (padding("top") + padding("bottom"));
        int x = padding("left");
        int y = padding("top");

        int x2 = x + width;
        int y2 = y + height;

        JSONObject area = new JSONObject();

        area.put("width", width);
        area.put("height", height);
        area.put("x", x);
        area.put("y", y);
        area.put("x2", x2);
        area.put("y2", y2);

        this.builderOptions.put("area", area);

    }

    public JSONObject area() {
        return this.builderOptions.getJSONObject("area");
    }

    public int area(String key) {
        return area().getInt(key);
    }

    public int width() {
        return area("width");
    }

    public int height() {
        return area("height");
    }

    public int x() {
        return area("x");
    }

    public int y() {
        return area("y");
    }

    public int x2 () {
        return area("x2");
    }

    public int y2() {
        return area("y2");
    }

    public int padding(String key) {
        return this.options.getJSONObject("padding").getInt(key);
    }

    public ChartBuilder addGrid(Grid grid) {
        return this;
    }

    private JSONObject cloneObject(String key) {
        return new JSONObject(this.options, new String[] { key });
    }

    private JSONArray cloneArray(String key) {
        return new JSONArray(this.options.getJSONArray(key));
    }

    private Object clone(String key) {
        return this.options.get(key);
    }

    public String color(int index, JSONArray colors) {
        String color = null;

        if (colors != null) {
            color = colors.getString(index);
        }

        if (color == null) {
            color = bobject("theme").getJSONArray("colors").getString(index);
        }

        if (bobject("hash").has(color)) {
            return "url(#" + bobject("hash").getString(color) +  ")";
        }

        return getColor(color);
    }

    private String getColor(JSONObject color) {
        return createGradient(color);
    }

    private String getColor(String color) {

        Object parsedColor = ColorUtil.parse(color);
        if (parsedColor instanceof String)
            return color;

        return createGradient(parsedColor, color);

    }

    @Override
    public void drawBefore() {

        JSONObject series = cloneObject("series");
        JSONObject grid = cloneObject("grid");
        Object brush = clone("brush");
        Object widget = clone("widget");

        JSONArray data = barray("data");

        // series 데이타 구성
        for (int i = 0, len = data.length(); i < len; i++) {
            JSONObject row = data.getJSONObject(i);

            Iterator it = row.keys();

            while(it.hasNext()) {
                String key = (String)it.next();

                if (!series.has(key)) {
                    series.put(key, new JSONObject());
                }

                JSONObject obj = series.getJSONObject(key);
                double value = row.getDouble(key);

                if (!obj.has("min")) obj.put("min", 0);
                if (!obj.has("max")) obj.put("max", 0);

                if (value < obj.getDouble("min")) obj.put("min", value);
                if (value > obj.getDouble("max")) obj.put("max", value);
            }
        }

        // series_list
        barray("brush", createBrushData(brush, series.names()));
        barray("widget", createBrushData(widget, series.names()));
        bobject("grid", grid);
        bobject("hash", null);

    }

    private JSONArray createBrushData(Object brush, JSONArray series_list) {

        JSONArray list = new JSONArray();

        if (brush != null) {
            if (brush instanceof String) {
                JSONObject o = new JSONObject();
                o.put("type", (String)brush);
                list.put(o);
            } else if (brush instanceof JSONObject) {
                list.put(brush);
            } else if (brush instanceof JSONArray){
                list = new JSONArray((JSONArray)brush);
            }

            for(int i = 0, len = list.length(); i < len; i++) {
                JSONObject b = list.getJSONObject(i);
                if (b.isNull("target")) {
                    b.put("target", series_list);
                } else if (b.get("target") instanceof String) {
                    b.put("target", new JSONArray().put(b.getString("target")));
                }
            }
        }

        return list;
    }

    private JSONArray barray(String key) {
        return this.builderOptions.getJSONArray(key);
    }

    private void barray(String key, JSONArray value) {
        if (value == null) {
            value = new JSONArray();
        }
        this.builderOptions.put(key, value);
    }

    private JSONObject bobject(String key) {
        return this.builderOptions.getJSONObject(key);
    }

    private void bobject(String key, JSONObject value) {
        if (value == null) {
            value = new JSONObject();
        }
        this.builderOptions.put(key, value);
    }

    @Override
    public Object draw() {
        return null;
    }

    public Object render() {

        caculate();

        drawBefore();

        return null;
    }
}
