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
import static com.jennifer.ui.util.Option.opt;

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
    private Option options;
    private Option builderOptions = opt();
    private HashMap<String, Class> grids = new HashMap<String, Class>();
    private HashMap<String, Class> brushes = new HashMap<String, Class>();
    private HashMap<String, Class> widgets = new HashMap<String, Class>();
    private Svg svg;
    private Option themeList = new Option();
    private Transform defs;
    private String clipId;
    private Transform root;

    public ChartBuilder() {
        this(opt());
    }

    public ChartBuilder(String json) {
        this(new Option(json));
    }

    public ChartBuilder(Option o) {
        this.setOptions(o);

        init();
    }

    public ChartBuilder(JSONObject jsonObject) {
        this(JSONUtil.clone(jsonObject));
    }

    public ChartBuilder(int width, int height) {
        this(opt().width(width).height(height));
    }

    public ChartBuilder(int width, int height, String theme) {
        this(opt().width(width).height(height).put("theme", theme));
    }

    public void setOptions(Option options) {
        this.options = options;

        if (!this.options.has("brush")) {
            this.options.put("brush", new OptionArray());
        }

        if (!this.options.has("widget")) {
            this.options.put("widget", new OptionArray());
        }

        if (!this.options.has("data")) {
            this.options.put("data", new OptionArray());
        }

        if (!this.options.has("grid")) {
            this.options.put("grid", new Option());
        }
    }

    public Option getOptions() {
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

        //TODO: support style option
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
        return grid(axis, JSONUtil.clone(o));
    }

    public ChartBuilder grid(String axis, Option o) {

        Option grid = (Option) options.object("grid");

        if (!grid.has(axis)) {
            grid.put(axis, new OptionArray());
        }

        OptionArray array = (OptionArray) grid.array(axis);
        array.put(o);

        return this;
    }

    public ChartBuilder brush(JSONObject o) {
        return brush(JSONUtil.clone(o));
    }

    public ChartBuilder brush(Option o) {
        OptionArray brush = (OptionArray)options.get("brush");

        brush.put(o);

        return this;
    }

    public ChartBuilder widget(JSONObject o) {
        return widget(JSONUtil.clone(o));
    }
    public ChartBuilder widget(Option o) {
        OptionArray widget = (OptionArray)options.get("widget");

        widget.put(o);

        return this;
    }


    private void initSvg() {
        Option o = new Option();
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

    private void addTheme(String name, Option themeObj) {
        themeList.put(name, themeObj);
    }

    private void initPadding() {
        builderOptions.width(options.optInt("width", 400));
        builderOptions.height(options.optInt("height", 400));

        // default Option
        if (this.options.has("padding")) {

            Object padding = this.options.get("padding");

            if (padding instanceof String && "empty".equals((String)padding)) {
                Option o = new Option().left(0).right(0).top(0).bottom(0);
                this.builderOptions.put("padding", o);
            } else {
                JSONObject source = (JSONObject)padding;
                Option o = new Option();
                o.put("left", source.optInt("left", 50));
                o.put("right", source.optInt("right", 50));
                o.put("bottom", source.optInt("bottom", 50));
                o.put("top", source.optInt("top", 50));

                this.builderOptions.put("padding", o);
            }

        } else {
            Option o = new Option().left(50).right(50).top(50).bottom(50);
            this.builderOptions.put("padding", o);
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
        return this.builderOptions.getInt(key);
    }

    private void caculate() {

        int width  = bi("width") - (padding("left") + padding("right"));
        int height  = bi("height") - (padding("top") + padding("bottom"));
        int x = padding("left");
        int y = padding("top");

        int x2 = x + width;
        int y2 = y + height;

        Option area = new Option();

        area.width(width).height(height).x(x).y(y).x2(x2).y2(y2);

        this.builderOptions.put("area", area);

    }

    public Option area() {
        return (Option)builderOptions.object("area");
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
        return builderOptions.object("padding").getInt(key);
    }

    private Option cloneObject(String key) {

        if (options.has(key)) {
            return JSONUtil.clone(options.object(key));
        } else {
            return opt();
        }
    }

    private OptionArray cloneArray(String key) {
        OptionArray list = new OptionArray();
        JSONArray source = options.array(key);

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
        return color(index, (OptionArray)colors);
    }
    public String color(int index, OptionArray colors) {
        String color = null;

        if (colors != null) {
            color = colors.getString(index);
        }

        if (color == null) {
            color = bobject("theme").array("colors").getString(index);
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
        return createGradient((Option)parsedColor, color);
    }

    private String createGradient(Option parsedColor, String hashKey) {

        Option hash = bobject("hash");

        if (hash.has(hashKey)) {
            return url(hash.getString(hashKey));
        }

        String id = StringUtil.createId("gradient");

        parsedColor.put("id", id);

        String type = parsedColor.string("type");
        Transform g = el(type + "Gradient", parsedColor);

        OptionArray stops = (OptionArray) parsedColor.array("stops");
        for (int i = 0, len = stops.length(); i < len; i++) {
            g.append(el("stop", (Option) stops.object(i)));
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

        Option series = cloneObject("series");
        Option grid = cloneObject("grid");
        Object brush = clone("brush");
        Object widget = clone("widget");

        OptionArray data = cloneArray("data");

        // series 데이타 구성
        for (int i = 0, len = data.length(); i < len; i++) {
            Option row = (Option) data.object(i);

            Iterator it = row.keys();

            while(it.hasNext()) {
                String key = (String)it.next();

                if (!series.has(key)) {
                    series.put(key, new Option());
                }

                Option obj = JSONUtil.clone(series.object(key));

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

    private OptionArray createBrushData(Object brush, JSONArray series_list) {

        OptionArray list = new OptionArray();

        if (brush != null) {

            if (brush instanceof String) {
                Option o = new Option();
                o.put("type", brush);
                list.put(o);
            } else if (brush instanceof Option) {
                list.put(brush);
            } else if (brush instanceof JSONObject) {
                list.put(JSONUtil.clone((JSONObject)brush));
            } else if (brush instanceof OptionArray){
                list = (OptionArray)brush;
            } else if (brush instanceof JSONArray) {
                list = JSONUtil.clone((JSONArray) brush);
            }

            for(int i = 0, len = list.length(); i < len; i++) {
                JSONObject b = list.object(i);
                if (b.isNull("target")) {
                    b.put("target", series_list);
                } else if (b.get("target") instanceof String) {
                    b.put("target", new OptionArray().put(b.getString("target")));
                }
            }
        }

        return list;
    }

    private OptionArray barray(String key) {
        return builderOptions.has(key) ? (OptionArray) builderOptions.array(key) : new OptionArray();
    }

    private void barray(String key, OptionArray value) {
        if (value == null) {
            value = new OptionArray();
        }
        this.builderOptions.put(key, value);
    }

    private Option bobject(String key) {
        return (Option) this.builderOptions.object(key);
    }

    private void bobject(String key, Option value) {
        if (value == null) {
            value = opt();
        }
        this.builderOptions.put(key, value);
    }

    @Override
    public Object draw() {
        return null;
    }

    private void drawObject(String type) {

        if (builderOptions.has(type) && !builderOptions.isNull(type)) {
            OptionArray list = (OptionArray) builderOptions.array(type);

            for(int i = 0, len = list.length(); i < len; i++) {
                JSONObject obj = list.object(i);
                String objType = obj.getString("type");
                Class cls = "brush".equals(type) ? brushes.get(objType) : widgets.get(objType);

                Option drawObject;
                if ("widget".equals(type)) {
                    drawObject = JSONUtil.clone(builderOptions.array(type).getJSONObject(i));
                } else {
                    drawObject = JSONUtil.clone(obj);
                }

                setGridAxis(obj, drawObject);

                obj.put("index", i);

                // create object and rendering

                try {
                    Drawable drawable = (Drawable) cls.getDeclaredConstructor(ChartBuilder.class, JSONObject.class).newInstance(this, obj);
                    Option result = (Option)drawable.render();

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

    private void setGridAxis(JSONObject obj, Option drawObject) {
        obj.remove("x");
        obj.remove("y");
        obj.remove("c");

        Option scales = (Option) builderOptions.object("scales");

        if (scales.has("x") || scales.has("x1")) {
             if (drawObject.has("x1") && drawObject.getInt("x1")  > -1) {
                 obj.put("x", scales.array("x1").get(drawObject.I("x1")));
             } else {
                 obj.put("x", scales.array("x").get(drawObject.optInt("x", 0)));
             }
        }

        if (scales.has("y") || scales.has("y1")) {
            if (drawObject.has("y1") && drawObject.I("y1")  > -1) {
                obj.put("y", scales.array("y1").get(drawObject.I("y1")));
            } else {
                obj.put("y", scales.array("y").get(drawObject.optInt("y", 0)));
            }
        }

        if (scales.has("c")) {
            obj.put("c", scales.array("c").get(drawObject.optInt("c", 0)));
        }

    }

    private void drawWidget() {
        drawObject("widget");
    }

    private void drawBrush() {
        drawObject("brush");
    }

    private void drawGrid() {
        Option grid = (Option) builderOptions.object("grid");

        if (grid != null && grid.names().length() > 0) {

            // create default cusotm grid
            if (grid.has("type")) {
                grid = (Option)opt().put("c", new OptionArray().put(JSONUtil.clone(grid)));
            }

            if (!builderOptions.has("scales")) {
                builderOptions.put("scales", new Option());
            }

            Option scales = (Option) builderOptions.object("scales");

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
                    scales.put(key, new OptionArray());
                }

                OptionArray scale = (OptionArray) scales.array(key);

                Object objGrid = grid.get(key);

                if (!(objGrid instanceof OptionArray) && !(objGrid instanceof JSONArray)) {
                    OptionArray o = new OptionArray();
                    o.put(JSONUtil.clone(grid.object(key)));

                    grid.put(key, o);
                } else if (objGrid instanceof JSONArray) {
                    grid.put(key, JSONUtil.clone((JSONArray)objGrid));
                }

                OptionArray gridObject = (OptionArray) grid.array(key);

                for(int keyIndex = 0, gridLen = gridObject.length(); keyIndex < gridLen; keyIndex++) {

                    Option g = JSONUtil.clone(gridObject.object(keyIndex));

                    Class cls = grids.get(g.string("type"));
                    Grid newGrid = null;

                    try {
                        newGrid = (Grid) cls.getDeclaredConstructor(Orient.class, ChartBuilder.class, Option.class).newInstance(orient, this, g);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    Option ret = (Option) newGrid.render();

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

                    this.root.append(root);

                    scales.array(key).put(keyIndex, newGrid);

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

        Option o = new Option().id(this.clipId);

        Option rect = new Option().x(0).y(0).width(width()).height(height());

        this.defs.clipPath(o).rect(rect);

    }

    public ChartBuilder add(Option o) {
        options.array("data").put(o);
        return this;
    }

    public ChartBuilder data(OptionArray data) {
        options.put("data", data);
        return this;
    }

    public ChartBuilder data(JSONArray data) {
        return data(JSONUtil.clone(data));
    }
    public OptionArray data() {
        return barray("data");
    }
    public Option data(int i) {
        return (Option) barray("data").object(i);
    }

    public Object data(int i, String key) {
        return barray("data").object(i).get(key);
    }

    public double dataDouble(int i, String key) {
        return barray("data").object(i).getDouble(key);
    }

    public Option series() {
        return bobject("series");
    }

    public OptionArray brush() {
        return barray("brush");
    }

    public Option brush(int index) {
        return JSONUtil.clone(barray("brush").object(index));
    }

    public Option series(String key) {
        return (Option) series().object(key);
    }

    public Option theme() {
        return (Option) builderOptions.object("theme");
    }

    public double themeDouble(String key) {
        return Double.parseDouble(theme(key).replaceAll("px", ""));
    }

    public String theme(String key) {
        Option theme = theme();

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
        setTheme(themeList.object(theme));
    }

    public void setTheme(JSONObject o) { builderOptions.put("theme", o); }

    public void setTheme(Option o) { builderOptions.put("theme", o); }

    public void setTheme(String key, String value) {
        theme().put(key, value);
    }

    public Transform text(Option textOpt, String text) {
        Option o = opt();
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
