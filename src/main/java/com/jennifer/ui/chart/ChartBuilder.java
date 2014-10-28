package com.jennifer.ui.chart;

import com.jennifer.ui.chart.brush.EqualizerBrush;
import com.jennifer.ui.chart.brush.*;
import com.jennifer.ui.chart.grid.*;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.Scale;
import com.jennifer.ui.util.dom.Svg;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.jennifer.ui.util.DomUtil.el;

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
    private Svg svg;
    private JSONObject themeList = new JSONObject();

    public ChartBuilder() {
        this(new JSONObject());
    }

    public ChartBuilder(JSONObject o) {
        this.options = o;

        init();

        caculate();
    }

    private void init() {

        initPadding();

        initTheme();

        // default grid
        initGrid();

        // default brush
        initBrush();

        // default widget
        initWidget();

        // svg main

        initSvg();

        setTheme(options.optString("theme", "jennifer"));

        //TODO: support style option
        if (options.has("style")) {

        }

    }

    private void initSvg() {
        JSONObject o = new JSONObject();
        o.put("width", bi("width"));
        o.put("height", bi("height"));

        this.svg = new Svg(o);
    }

    private void initWidget() {


    }

    private void initBrush() {
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
    }

    private void initGrid() {
        addGrid("block", BlockGrid.class);
        addGrid("range", RangeGrid.class);
        addGrid("date", DateGrid.class);
        addGrid("rule", RuleGrid.class);
        addGrid("radar", RadarGrid.class);
    }

    private void initTheme() {

        addTheme("jennifer", JSONUtil.load("chart/theme/jennifer.json"));
        addTheme("dark", JSONUtil.load("chart/theme/dark.json"));
    }

    private void addTheme(String name, JSONObject themeObj) {
        themeList.put(name, themeObj);
    }

    private void initPadding() {
        builderOptions.put("width", options.optInt("width", 400));
        builderOptions.put("height", options.optInt("height", 400));


        // default Option
        if (this.options.has("padding")) {

            Object padding = this.options.get("padding");

            if (padding instanceof String && "empty".equals((String)padding)) {
                JSONObject o = new JSONObject();
                o.put("left", 0);
                o.put("right", 0);
                o.put("bottom", 0);
                o.put("top", 0);
                this.builderOptions.put("padding", o);
            } else {
                JSONObject source = (JSONObject)padding;
                JSONObject o = new JSONObject();
                o.put("left", source.optInt("left", 50));
                o.put("right", source.optInt("right", 50));
                o.put("bottom", source.optInt("bottom", 50));
                o.put("top", source.optInt("top", 50));

                this.builderOptions.put("padding", o);
            }

        } else {
            JSONObject o = new JSONObject();
            o.put("left", 50);
            o.put("right", 50);
            o.put("bottom", 50);
            o.put("top", 50);
            this.builderOptions.put("padding", o);
        }
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

        int width  = bi("width") - (padding("left") + padding("right"));
        int height  = bi("height") - (padding("top") + padding("bottom"));
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
        return this.builderOptions.getJSONObject("padding").getInt(key);
    }

    public ChartBuilder addGrid(Grid grid) {
        return this;
    }

    private JSONObject cloneObject(String key) {
        if (options.has(key)) {
            return JSONUtil.extend(new JSONObject(), options.getJSONObject(key));
        } else {
            return new JSONObject();
        }
    }

    private JSONArray cloneArray(String key) {
        JSONArray list = new JSONArray();
        JSONArray source = this.options.getJSONArray(key);

        if (source != null) {
            for(int i = 0, len = source.length(); i < len; i++) {
                list.put(JSONUtil.clone(source.getJSONObject(i)));
            }
        }

        return list;
    }

    private Object clone(String key) {
        return this.options.has(key) ? this.options.get(key) : null;
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

        return color;
        /*

        Object parsedColor = ColorUtil.parse(color);

        if (parsedColor instanceof String) {
            return (String)parsedColor;
        }
        return createGradient((JSONObject)parsedColor, color);
        */
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

        JSONArray data = cloneArray("data");

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

                if (row.get(key) instanceof String) break;

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
        barray("data", data);

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
        return builderOptions.has(key) ? builderOptions.getJSONArray(key) : new JSONArray();
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
                    JSONObject result = (JSONObject)drawable.render();

                    Transform root = (Transform)result.get("root");

                    this.svg.append(root);

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

            if (!builderOptions.has("scales")) {
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
                    o.put(JSONUtil.extend(new JSONObject(), grid.getJSONObject(key)));

                    grid.put(key, o);
                }

                JSONArray gridObject = grid.getJSONArray(key);


                for(int keyIndex = 0, gridLen = gridObject.length(); keyIndex < gridLen; keyIndex++) {

                    JSONObject g = gridObject.getJSONObject(keyIndex);

                    Class cls = grids.get(g.getString("type"));
                    Grid newGrid = null;

                    try {
                        newGrid = (Grid) cls.getDeclaredConstructor(Orient.class, ChartBuilder.class, JSONObject.class).newInstance(orient, this, g);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    JSONObject ret = (JSONObject)newGrid.render();

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

                    this.svg.append(root);

                    scales.getJSONArray(key).put(keyIndex, newGrid);

                }

            }
        }
    }

    public String render() {

        caculate();

        drawBefore();

        drawGrid();
        drawBrush();
        drawWidget();

        return this.svg.render();
    }

    public JSONArray data() {
        return barray("data");
    }

    public Object data(int i, String key) {
        return barray("data").getJSONObject(i).get(key);
    }

    public double dataDouble(int i, String key) {
        return barray("data").getJSONObject(i).getDouble(key);
    }

    public JSONObject series() {
        return bobject("series");
    }

    public JSONObject series(String key) {
        return series().getJSONObject(key);
    }

    public JSONObject theme() {
        return builderOptions.getJSONObject("theme");
    }

    public String theme(String key) {
        JSONObject theme = theme();

        if (theme.has(key) ) {
            if (key.indexOf("Color") > -1) {
                return getColor(theme.get(key).toString());
            } else {
                return theme.get(key).toString();
            }
        }

        return "";
    }

    public String theme(boolean checked, String key1, String key2) {
        String value = (checked) ? key1 : key2;

        return theme(value);
    }

    public void setTheme(String theme) {
        setTheme(themeList.getJSONObject(theme));
    }

    public void setTheme(JSONObject o) {
        builderOptions.put("theme", o);
    }

    public void setTheme(String key, String value) {
        theme().put(key, value);
    }

    public Transform text(JSONObject textOpt, String text) {
        JSONObject o = new JSONObject();

        o.put("font-family", theme("fontFamily"));
        o.put("font-size", theme("fontSize"));
        o.put("fill", theme("fontColor"));

        return (Transform) el("text", JSONUtil.extend(o, textOpt)).textNode(text);
    }

}
