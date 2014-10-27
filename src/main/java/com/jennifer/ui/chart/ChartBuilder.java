package com.jennifer.ui.chart;

import brush.EqualizerBrush;
import com.jennifer.ui.chart.brush.*;
import com.jennifer.ui.chart.grid.*;
import com.jennifer.ui.common.ChartData;
import com.jennifer.ui.util.ColorUtil;
import com.jennifer.ui.util.DomUtil;
import com.jennifer.ui.util.Scale;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.org.mozilla.javascript.internal.ast.Block;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.RunnableFuture;

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
    private HashMap<String, Class> grids = new HashMap<String, Class>();
    private HashMap<String, Class> brushes = new HashMap<String, Class>();
    private HashMap<String, Class> widgets = new HashMap<String, Class>();

    public ChartBuilder() {
        this(new JSONObject());
    }

    public ChartBuilder(JSONObject o) {
        this.options = o;

        caculate();
        initPlugin();
    }

    private void initPlugin() {

        // annotation scan

        // default grid
        addGrid("block", BlockGrid.class);
        addGrid("range", RangeGrid.class);
        addGrid("date", DateGrid.class);
        addGrid("rule", RuleGrid.class);
        addGrid("radar", RadarGrid.class);

        // default brush
        addBrush("area",                AreaBrush.class);
        addBrush("bar",                 BarBrush.class);
        addBrush("bargauge",            BarGaugeBrush.class);
        addBrush("bubble",              BubbleBrush.class);
        addBrush("candlestick",         CandleStickBrush.class);
        addBrush("circlegauge",         CircleGaugeBrush.class);
        addBrush("column",              ColumnBrush.class);
        addBrush("donut",               DonutBrush.class);
        addBrush("equalizer",           EqualizerBrush.class);
        addBrush("fillgauge",           FillGaugeBrush.class);
        addBrush("fullgauge",           FullGaugeBrush.class);
        addBrush("fullstack",           FullStackBrush.class);
        addBrush("gauge",               GagueBrush.class);
        addBrush("line",                LineBrush.class);
        addBrush("ohlc",                OhlcBrush.class);
        addBrush("path",                PathBrush.class);
        addBrush("pie",                 PieBrush.class);
        addBrush("scatter",             ScatterBrush.class);
        addBrush("scatterpath",         ScatterPathBrush.class);
        addBrush("stackarea",           StackAreaBrush.class);
        addBrush("stackbar",            StackBarBrush.class);
        addBrush("stackcolumn",         StackColumnBrush.class);
        addBrush("stackgauge",          StackGagueBrush.class);
        addBrush("stackline",           StackLineBrush.class);
        addBrush("stackscatter",        StackScatterBrush.class);


        // default widget
    }

    private void addBrush(String key, Class brushClass) {
        brushes.put(key, brushClass);
    }

    private void addWidget(String key, Class widgetClass) {
        widgets.put(key, widgetClass);
    }

    private void addGrid(String key, Class gridClass) {
        grids.put(key, gridClass);
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

    private String createGradient(JSONObject color) {
        return null;
    }

    private String getColor(String color) {

        Object parsedColor = ColorUtil.parse(color);

        if (parsedColor instanceof String) {
            return (String)parsedColor;
        }
        return createGradient((JSONObject)parsedColor, color);

    }

    private String createGradient(JSONObject parsedColor, String color) {
        return null;
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

    private void drawObject(String type) {
        if (builderOptions.has(type) && !builderOptions.isNull(type)) {
            JSONArray list = builderOptions.getJSONArray(type);

            for(int i = 0, len = list.length(); i < len; i++) {
                JSONObject obj = list.getJSONObject(i);
                String objType = obj.getString("type");
                Class cls = "brush".equals(type) ? brushes.get(objType) : widgets.get(objType);

                JSONObject drawObject;
                if ("widget".equals(type)) {
                    drawObject = builderOptions.getJSONArray("brush").getJSONObject(i);
                } else {
                    drawObject = obj;
                }

                setGridAxis(obj, drawObject);

                obj.put("index", i);

                // create object and rendering

                try {
                    Drawable drawable = (Drawable) cls.getDeclaredConstructor(ChartBuilder.class, JSONObject.class).newInstance(this, obj);
                    drawable.render();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void setGridAxis(JSONObject obj, JSONObject drawObject) {
        obj.remove("x");
        obj.remove("y");
        obj.remove("c");

        JSONObject scales = builderOptions.getJSONObject("scales");

        if (scales.has("x") || scales.has("x1")) {

            Scale scaleX;
            Scale scaleY;
            Scale scaleC;

             if (drawObject.has("x1") && drawObject.getInt("x1")  > -1) {
                 scaleX = (Scale) scales.getJSONArray("x1").get(drawObject.getInt("x1"));

                 obj.put("x", scaleX);
             } else {
                 if (drawObject.has("x")) {
                     scaleX = (Scale) scales.getJSONArray("x").get(drawObject.getInt("x"));
                 } else {
                     scaleX = (Scale) scales.getJSONArray("x").get(0);
                 }

                 obj.put("x", scaleX);
             }

             if (drawObject.has("y1") && drawObject.getInt("y1")  > -1) {
                 scaleY = (Scale) scales.getJSONArray("y1").get(drawObject.getInt("y1"));

                 obj.put("y", scaleY);
             } else {
                 if (drawObject.has("y")) {
                     scaleY = (Scale) scales.getJSONArray("y").get(drawObject.getInt("y"));
                 } else {
                     scaleY = (Scale) scales.getJSONArray("y").get(0);
                 }

                 obj.put("y", scaleY);
             }


             if (drawObject.has("c") && drawObject.getInt("c")  > -1) {
                 scaleC = (Scale) scales.getJSONArray("c").get(drawObject.getInt("c"));

                 obj.put("c", scaleC);

             }



        }


    }

    private void drawWidget() {
        drawObject("widget");
    }

    private void drawBrush() {
        drawObject("brush");
    }

    private void drawGrid() {
        JSONObject grid = builderOptions.getJSONObject("grid");

        if (grid != null) {

            // create default cusotm grid
            if (grid.has("type")) {
                grid = new JSONObject();
                grid.put("c", new JSONArray(grid));
            }

            if (builderOptions.has("scales")) {
                builderOptions.put("scales", new JSONObject());
            }

            JSONObject scales = builderOptions.getJSONObject("scales");

            JSONArray keys = grid.names();

            for(int i = 0, len = keys.length(); i < len; i++) {
                String key = keys.getString(i);

                Orient orient = Orient.CUSTOM;

                if ("x".equals(key)) {
                    orient = Orient.BOTTOM;
                } else if ("y".equals(key)) {
                    orient = Orient.LEFT;
                } else if ("x1".equals(key)) {
                    orient = Orient.TOP;
                } else if ("y1".equals(key)) {
                    orient = Orient.RIGHT;
                }

                if (!scales.has(key)) {
                    scales.put(key, new JSONArray());
                }

                JSONArray scale = scales.getJSONArray(key);

                if (!(grid.get(key) instanceof JSONArray)) {
                    JSONArray o = new JSONArray();
                    o.put(grid);

                    grid = new JSONObject();
                    grid.put(key, o);
                }

                JSONArray gridObject = grid.getJSONArray(key);

                for(int keyIndex = 0, gridLen = gridObject.length(); keyIndex < gridLen; keyIndex++) {

                    JSONObject g = gridObject.getJSONObject(keyIndex);

                    Class cls = grids.get(g.getString("type"));
                    Drawable drawable = null;

                    try {
                        drawable = (Drawable) cls.getDeclaredConstructor(ChartBuilder.class, JSONObject.class).newInstance(this, g);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    JSONObject ret = (JSONObject)drawable.render();

                    int dist = g.optInt("dist", 0);
                    Transform root = (Transform)ret.get("root");

                    if ("y".equals(key)) {
                        root.translate(x() - dist, y());
                    } else if ("y1".equals(key)) {
                        root.translate(x2() + dist, y());
                    } else if ("x".equals(key)) {
                        root.translate(x(), y2() + dist);
                    } else if ("x1".equals(key)) {
                        root.translate(x(), y() - dist);
                    }

                    scales.getJSONArray(key).put(keyIndex, (Scale)ret.get("scale"));

                }

            }
        }
    }

    public Object render() {

        caculate();

        drawBefore();

        drawGrid();
        drawBrush();
        drawWidget();

        return null;
    }

    public JSONArray data() {
        return barray("data");
    }

    public JSONObject series() {
        return bobject("series");
    }

    public JSONObject series(String key) {
        return series().getJSONObject(key);
    }
}
