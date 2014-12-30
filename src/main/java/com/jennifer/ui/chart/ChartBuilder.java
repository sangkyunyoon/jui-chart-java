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

package com.jennifer.ui.chart;

import com.jennifer.ui.chart.brush.EqualizerBrush;
import com.jennifer.ui.chart.brush.*;
import com.jennifer.ui.chart.grid.*;
import com.jennifer.ui.chart.widget.*;
import com.jennifer.ui.util.*;
import com.jennifer.ui.util.dom.Svg;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private JSONObject options;
    private JSONObject builderoptions = new JSONObject();
    private HashMap<String, Class> grids = new HashMap<String, Class>();
    private HashMap<String, Class> brushes = new HashMap<String, Class>();
    private HashMap<String, Class> widgets = new HashMap<String, Class>();
    private Svg svg;
    private JSONObject themeList = new JSONObject();
    private Transform defs;
    private String clipId;
    private Transform root;

    public ChartBuilder() {
        this(new JSONObject());
    }

    public ChartBuilder(String json) {
        this(new JSONObject(json));
    }

    public ChartBuilder(JSONObject o) {
        this.setOptions(o);

        init();
    }

    public ChartBuilder(int width, int height) {
        this(new JSONObject().put("width", width).put("height", height));
    }

    public ChartBuilder(int width, int height, String theme) {
        this(new JSONObject().put("width", width).put("height", height).put("theme", theme));
    }

    public void setOptions(JSONObject options) {
        this.options = options;

        if (!this.options.has("brush")) {
            this.options.put("brush", new JSONArray());
        }

        if (!this.options.has("widget")) {
            this.options.put("widget", new JSONArray());
        }

        if (!this.options.has("data")) {
            this.options.put("data", new JSONArray());
        }

        if (!this.options.has("grid")) {
            this.options.put("grid", new JSONObject());
        }
    }

    public JSONObject getOptions() {
        return this.options;
    }


    public boolean set(String jsonPath, Object value) {
        return JSONUtil.set(this.options, jsonPath, value);
    }

    public Object get(String jsonPath) {
        return JSONUtil.get(this.options, jsonPath);
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

        //TODO: support style JSONObject
        if (options.has("style")) {

        }

    }

    public ChartBuilder gridDateFormat(String axis, int index, String key, ChartDateFormat value) {
        JSONObject grid  = options.getJSONObject("grid");

        if (grid.has(axis)) {
            JSONObject o = grid.getJSONArray(axis).optJSONObject(index);
            o.put(key, value);
        }

        return this;
    }


    public ChartBuilder grid(String axis, JSONObject o) {

        JSONObject grid = options.getJSONObject( "grid");

        if (!grid.has(axis)) {
            grid.put(axis, new JSONArray());
        }

        JSONArray array = grid.getJSONArray( axis);
        array.put(o);

        return this;
    }

    public ChartBuilder brush(JSONObject o) {
        JSONArray brush = (JSONArray)options.get("brush");

        brush.put(o);

        return this;
    }

    public ChartBuilder widget(JSONObject o) {
        JSONArray widget = (JSONArray)options.get("widget");

        widget.put(o);

        return this;
    }


    private void initSvg() {
        JSONObject o = new JSONObject();
        o.put("width", bi("width"));
        o.put("height", bi("height"));

        this.svg = new Svg(o);
        this.root = this.svg.g().translate(0.5, 0.5);
    }

    private void initWidget() {
        plugin("widget", "title", TitleWidget.class);
        plugin("widget", "legend", LegendWidget.class);

    }

    private void initBrush() {
        plugin("brush", "area", AreaBrush.class);
        plugin("brush", "bar", BarBrush.class);
        plugin("brush", "bargauge", BarGaugeBrush.class);
        plugin("brush", "bubble", BubbleBrush.class);
        plugin("brush", "candlestick", CandleStickBrush.class);
        plugin("brush", "circlegauge", CircleGaugeBrush.class);
        plugin("brush", "column", ColumnBrush.class);
        plugin("brush", "donut", DonutBrush.class);
        plugin("brush", "equalizer", EqualizerBrush.class);
        plugin("brush", "fillgauge", FillGaugeBrush.class);
        plugin("brush", "fullgauge", FullGaugeBrush.class);
        plugin("brush", "fullstack", FullStackBrush.class);
        plugin("brush", "gauge", GagueBrush.class);
        plugin("brush", "line", LineBrush.class);
        plugin("brush", "ohlc", OhlcBrush.class);
        plugin("brush", "path", PathBrush.class);
        plugin("brush", "pie", PieBrush.class);
        plugin("brush", "scatter", ScatterBrush.class);
        plugin("brush", "scatterpath", ScatterPathBrush.class);
        plugin("brush", "stackarea", StackAreaBrush.class);
        plugin("brush", "stackbar", StackBarBrush.class);
        plugin("brush", "stackcolumn", StackColumnBrush.class);
        plugin("brush", "stackgauge", StackGagueBrush.class);
        plugin("brush", "stackline", StackLineBrush.class);
        plugin("brush", "stackscatter", StackScatterBrush.class);
    }

    private void initGrid() {
        plugin("grid", "block", BlockGrid.class);
        plugin("grid", "range", RangeGrid.class);
        plugin("grid", "date", DateGrid.class);
        plugin("grid", "rule", RuleGrid.class);
        plugin("grid", "radar", RadarGrid.class);
    }

    private void initTheme() {
        addTheme("jennifer", JSONUtil.loadJSONFile("chart/theme/jennifer.json"));
        addTheme("dark", JSONUtil.loadJSONFile("chart/theme/dark.json"));
        addTheme("gradient", JSONUtil.loadJSONFile("chart/theme/gradient.json"));
        addTheme("pastel", JSONUtil.loadJSONFile("chart/theme/pastel.json"));
    }

    private void addTheme(String name, JSONObject themeObj) {
        themeList.put(name, themeObj);
    }

    private void initPadding() {
        builderoptions.put("width", options.optInt("width", 400));
        builderoptions.put("height", options.optInt("height", 400));

        if (this.options.has("padding")) {

            Object padding = this.options.get("padding");

            if (padding instanceof String && "empty".equals((String)padding)) {
                JSONObject o = new JSONObject().put("left", 0).put("right", 0).put("top", 0).put("bottom", 0);
                this.builderoptions.put("padding", o);
            } else {
                JSONObject source = (JSONObject)padding;
                JSONObject o = new JSONObject();
                o.put("left", source.optInt("left", 50));
                o.put("right", source.optInt("right", 50));
                o.put("bottom", source.optInt("bottom", 50));
                o.put("top", source.optInt("top", 50));

                this.builderoptions.put("padding", o);
            }

        } else {
            JSONObject o = new JSONObject().put("left", 50).put("right", 50).put("top", 50).put("bottom", 50);
            this.builderoptions.put("padding", o);
        }
    }
    public void plugin(String type, String name, Class cls) {
        if ("brush".equals(type)) {
            brushes.put(name, cls);
        } else if ("widget".equals(type)) {
            widgets.put(name, cls);
        } else if ("grid".equals(type)) {
            grids.put(name, cls);
        }
    }

    public int i(String key) {
        return this.options.getInt(key);
    }

    public int bi(String key) {
        return this.builderoptions.getInt(key);
    }

    private void caculate() {

        int width  = bi("width") - (padding("left") + padding("right"));
        int height  = bi("height") - (padding("top") + padding("bottom"));
        int x = padding("left");
        int y = padding("top");

        int x2 = x + width;
        int y2 = y + height;

        JSONObject area = new JSONObject();

        area.put("width", width).put("height", height).put("x", x).put("y", y).put("x2", x2).put("y2", y2);

        this.builderoptions.put("area", area);

    }

    public JSONObject area() {
        return (JSONObject)builderoptions.getJSONObject( "area");
    }

    public int area(String key) {
        return area().getInt(key);
    }

    public int padding(String key) {
        return builderoptions.getJSONObject( "padding").getInt(key);
    }

    private JSONObject cloneObject(String key) {

        if (options.has(key)) {
            return JSONUtil.clone(options.getJSONObject( key));
        } else {
            return new JSONObject();
        }
    }

    private JSONArray cloneArray(String key) {
        JSONArray list = new JSONArray();
        JSONArray source = options.getJSONArray( key);

        if (source != null) {
            for(int i = 0, len = source.length(); i < len; i++) {
                list.put(JSONUtil.clone(source.getJSONObject(i)));
            }
        }

        return list;
    }

    private Object clone(String key) {
        return options.opt(key);
    }

    public String color(int index, JSONArray colors) {
        String color = null;

        if (colors != null) {
            color = colors.getString(index);
        }

        if (color == null) {
            color = bobject("theme").getJSONArray( "colors").getString(index);
        }

        if (bobject("hash").has(color)) {
            return url(bobject("hash").getString(color));
        }

        return getColor(color);
    }

    private String getColor(String color) {

        Object parsedColor = ColorUtil.parse(color);

        if (parsedColor instanceof String) {
            return (String)parsedColor;
        }
        return createGradient((JSONObject)parsedColor, color);
    }

    private String createGradient(JSONObject parsedColor, String hashKey) {

        JSONObject hash = bobject("hash");

        if (hash.has(hashKey)) {
            return url(hash.getString(hashKey));
        }

        String id = StringUtil.createId("gradient");

        parsedColor.put("id", id);

        String type = parsedColor.getString("type");
        Transform g = el(type + "Gradient", parsedColor);

        JSONArray stops = (JSONArray) parsedColor.getJSONArray( "stops");
        for (int i = 0, len = stops.length(); i < len; i++) {
            g.append(el("stop", (JSONObject) stops.getJSONObject( i)));
        }
        parsedColor.remove("stops");

        this.defs.append(g);

        if (hashKey != null) {
            hash.put(hashKey, id);
        }

        return url(id);
    }

    public String url(String id) {
        return "url(#" + id + ")";
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
            JSONObject row = (JSONObject) data.getJSONObject( i);

            Iterator it = row.keys();

            while(it.hasNext()) {
                String key = (String)it.next();

                if (!series.has(key)) {
                    series.put(key, new JSONObject());
                }

                JSONObject obj = JSONUtil.clone(series.getJSONObject( key));

                if (obj == null) continue;

                Object valueObject = row.get(key);

                if (valueObject instanceof String) continue;

                if (valueObject instanceof Double || valueObject instanceof Integer ) {
                    double value = row.getDouble(key);

                    if (!obj.has("min")) obj.put("min", value);
                    if (!obj.has("max")) obj.put("max", value);

                    if (value < obj.getDouble("min")) obj.put("min", value);
                    if (value > obj.getDouble("max")) obj.put("max", value);
                } else if (row.get(key) instanceof Long) {
                    long value = row.getLong(key);

                    if (!obj.has("min")) obj.put("min", value);
                    if (!obj.has("max")) obj.put("max", value);

                    if (value < obj.getLong("min")) obj.put("min", value);
                    if (value > obj.getLong("max")) obj.put("max", value);
                }

                series.put(key, obj);
            }
        }
        // series_list
        barray("brush", createBrushData(brush, series.names()));
        barray("widget",createBrushData(widget,  series.names()));
        bobject("grid", grid);
        bobject("hash", null);
        barray("data", data);
        bobject("series", series);

    }

    private JSONArray createBrushData(Object brush, JSONArray series_list) {

        JSONArray list = new JSONArray();

        if (brush != null) {

            if (brush instanceof String) {
                JSONObject o = new JSONObject();
                o.put("type", brush);
                list.put(o);
            } else if (brush instanceof JSONObject) {
                list.put(brush);
            } else if (brush instanceof JSONObject) {
                list.put(JSONUtil.clone((JSONObject)brush));
            } else if (brush instanceof JSONArray){
                list = (JSONArray)brush;
            } else if (brush instanceof JSONArray) {
                list = JSONUtil.clone((JSONArray) brush);
            }

            for(int i = 0, len = list.length(); i < len; i++) {
                JSONObject b = list.getJSONObject( i);
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
        return builderoptions.has(key) ? (JSONArray) builderoptions.getJSONArray( key) : new JSONArray();
    }

    private void barray(String key, JSONArray value) {
        if (value == null) {
            value = new JSONArray();
        }
        this.builderoptions.put(key, value);
    }

    private JSONObject bobject(String key) {
        return (JSONObject) this.builderoptions.getJSONObject( key);
    }

    private void bobject(String key, JSONObject value) {
        if (value == null) {
            value = new JSONObject();
        }
        this.builderoptions.put(key, value);
    }

    @Override
    public Object draw() {
        return null;
    }

    private void drawObject(String type) {

        if (builderoptions.has(type) && !builderoptions.isNull(type)) {
            JSONArray list = (JSONArray) builderoptions.getJSONArray( type);

            for(int i = 0, len = list.length(); i < len; i++) {
                JSONObject obj = list.getJSONObject( i);
                String objType = obj.getString("type");
                Class cls = "brush".equals(type) ? brushes.get(objType) : widgets.get(objType);

                JSONObject drawObject;
                if ("widget".equals(type)) {
                    drawObject = JSONUtil.clone(builderoptions.getJSONArray( type).getJSONObject(i));
                } else {
                    drawObject = JSONUtil.clone(obj);
                }

                setGridAxis(obj, drawObject);

                obj.put("index", i);

                // create object and rendering

                try {
                    Drawable drawable = (Drawable) cls.getDeclaredConstructor(ChartBuilder.class, JSONObject.class).newInstance(this, obj);
                    JSONObject result = (JSONObject)drawable.render();

                    Transform root = (Transform)result.get("root");
                    root.addClass(type + " " + objType);

                    this.root.append(root);

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

        if (!builderoptions.has( "scales")) {
            return ;
        }

        JSONObject scales = builderoptions.getJSONObject( "scales");

        if (scales.has("x") || scales.has("x1")) {
             if (drawObject.has("x1") && drawObject.getInt("x1")  > -1) {
                 obj.put("x", scales.getJSONArray( "x1").get(drawObject.getInt("x1")));
             } else {
                 obj.put("x", scales.getJSONArray( "x").get(drawObject.optInt("x", 0)));
             }
        }

        if (scales.has("y") || scales.has("y1")) {
            if (drawObject.has("y1") && drawObject.getInt("y1")  > -1) {
                obj.put("y", scales.getJSONArray( "y1").get(drawObject.getInt("y1")));
            } else {
                obj.put("y", scales.getJSONArray( "y").get(drawObject.optInt("y", 0)));
            }
        }

        if (scales.has("c")) {
            obj.put("c", scales.getJSONArray( "c").get(drawObject.optInt("c", 0)));
        }

    }

    private void drawWidget() {
        drawObject("widget");
    }

    private void drawBrush() {
        drawObject("brush");
    }

    private void drawGrid() {
        JSONObject grid = builderoptions.getJSONObject( "grid");

        String[] names = JSONObject.getNames(grid);
        if (names == null) return;

        if (grid != null && grid.names().length() > 0) {

            // create default cusotm grid
            if (grid.has("type")) {
                grid = new JSONObject().put("c", new JSONArray().put(JSONUtil.clone(grid)));
            }

            if (!builderoptions.has("scales")) {
                builderoptions.put("scales", new JSONObject());
            }

            JSONObject scales = (JSONObject) builderoptions.getJSONObject( "scales");

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

                JSONArray scale = (JSONArray) scales.getJSONArray( key);

                Object objGrid = grid.get(key);

                if (!(objGrid instanceof JSONArray) && !(objGrid instanceof JSONArray)) {
                    JSONArray o = new JSONArray();
                    o.put(JSONUtil.clone(grid.getJSONObject( key)));

                    grid.put(key, o);
                } else if (objGrid instanceof JSONArray) {
                    grid.put(key, JSONUtil.clone((JSONArray)objGrid));
                }

                JSONArray gridObject = (JSONArray) grid.getJSONArray( key);

                for(int keyIndex = 0, gridLen = gridObject.length(); keyIndex < gridLen; keyIndex++) {

                    JSONObject g = JSONUtil.clone(gridObject.getJSONObject( keyIndex));

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

                    JSONObject ret = (JSONObject) newGrid.render();

                    int dist = g.optInt("dist", 0);
                    Transform root = (Transform)ret.get("root");

                    if ("y".equals(key)) {
                        root.translate(area("x") - dist, area("y"));
                    } else if ("y1".equals(key)) {
                        root.translate(area("x2") + dist, area("y"));
                    } else if ("x".equals(key)) {
                        root.translate(area("x"), area("y2") + dist);
                    } else if ("x1".equals(key)) {
                        root.translate(area("x"), area("y") - dist);
                    }

                    this.root.append(root);

                    scales.getJSONArray( key).put(keyIndex, newGrid);

                }

            }
        }
    }

    public String render() {

        caculate();

        drawBefore();
        drawDefs();
        drawGrid();
        drawBrush();
        drawWidget();

        this.svg.css("background", theme("backgroundColor"));

        return this.svg.render();
    }

    private void drawDefs() {
        this.defs = (Transform) this.root.defs();
        this.clipId = StringUtil.createId("clip-id");

        JSONObject o = new JSONObject().put("id", this.clipId);

        JSONObject rect = new JSONObject().put("x", 0).put("y", 0).put("width", area("width")).put("height", area("height"));

        this.defs.clipPath(o).rect(rect);

    }

    public ChartBuilder add(JSONObject o) {
        options.getJSONArray( "data").put(o);
        return this;
    }

    public ChartBuilder data(JSONArray data) {
        options.put("data", data);
        return this;
    }

    public JSONArray data() {
        return barray("data");
    }
    public JSONObject data(int i) {
        return (JSONObject) barray("data").getJSONObject( i);
    }

    public Object data(int i, String key) {
        return barray("data").getJSONObject( i).get(key);
    }

    public double dataDouble(int i, String key) {
        return barray("data").getJSONObject( i).getDouble(key);
    }

    public JSONObject series() {
        return bobject("series");
    }

    public JSONArray brush() {
        return barray("brush");
    }

    public JSONObject brush(int index) {
        return JSONUtil.clone(barray("brush").getJSONObject( index));
    }

    public JSONObject series(String key) {
        return (JSONObject) series().getJSONObject( key);
    }

    public JSONObject theme() {
        return (JSONObject) builderoptions.getJSONObject( "theme");
    }

    public double themeDouble(String key) {
        return Double.parseDouble(theme(key).replaceAll("px", ""));
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
        setTheme(themeList.getJSONObject( theme));
    }

    public void setTheme(JSONObject o) { builderoptions.put("theme", o); }

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

    public String clipId() {
        return url(clipId);
    }

    public Transform defs() {
        return defs;
    }

    public void writeFile(String saveFilename) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(saveFilename));

            out.write(this.render());

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
