package com.jennifer.ui.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by yuni on 2014-10-31.
 */
public class Option extends JSONObject {

    public Option(String s) {
        super(s);
    }

    public Option() {
        super();
    }

    // Setter
    public Option x(double x) { return (Option)put("x", x); }
    public Option y(double y) { return (Option)put("y", y); }
    public Option x1(double x1) { return (Option)put("x1", x1); }
    public Option y1(double y1) { return (Option)put("y1", y1); }
    public Option x2(double x2) { return (Option)put("x2", x2); }
    public Option y2(double y2) { return (Option)put("y2", y2); }
    public Option width(double width) { return (Option)put("width", width); }
    public Option height(double height) { return (Option)put("height", height); }
    public Option fill(String fill) { return (Option)put("fill", fill); }
    public Option stroke(String color) { return (Option)put("stroke", color); }
    public Option strokeWidth(int i) { return (Option)put("stroke-width", i); }
    public Option strokeWidth(double d) { return (Option)put("stroke-width", d); }
    public Option strokeWidth(String i) { return (Option)put("stroke-width", i); }
    public Option strokeOpacity(double o ) { return (Option)put("stroke-opacity", o); }
    public Option fillOpacity(String o) { return (Option)put("fill-opacity", o); }
    public Option textAnchor(String anchor) { return (Option)put("text-anchore", anchor); }
    public Option value(double value) { return (Option)put("value", value); }
    public Option clipPath(String url) { return (Option)put("clip-path", url); }
    public Option d(String path) {  return (Option)put("d", path); }
    public Option fontFamily(String fontFamily) { return (Option)put("font-family", fontFamily ); }
    public Option fontSize(String size) { return (Option)put("font-size", size); }
    public Option fontSize(double size) { return (Option)put("font-size", size); }
    public Option fontWeight(String fontWeight) { return (Option)put("font-weight", fontWeight); }
    public Option fontWeight(int fontWeight) { return (Option)put("font-weight", fontWeight); }

    public Option cx(double cx) { return (Option)put("cx", cx); }
    public Option cy(double cy) { return (Option)put("cy", cy); }
    public Option r(double r) { return (Option)put("r", r); }
    public Option rx(double r) { return (Option)put("rx", r); }
    public Option ry(double r) { return (Option)put("ry", r); }

    // Getter
    public double value() { return getDouble("value"); }
    public double x() { return getDouble("x"); }
    public double y() { return getDouble("y"); }
    public double x1() { return getDouble("x1"); }
    public double y1() { return getDouble("y1"); }
    public double x2() { return getDouble("x2"); }
    public double y2() { return getDouble("y2"); }
    public double width() { return getDouble("width"); }
    public double height() { return getDouble("height"); }
    public String fill() { return getString("fill"); }
    public String stroke() { return getString("stroke"); }
    public double strokeWidth() { return getDouble("stroke-width"); }
    public double strokeOpacity() { return getDouble("stroke-opacity"); }
    public String textAnchor() { return getString("text-anchor"); }

    public double cx() { return getDouble("cx"); }
    public double cy() { return getDouble("cy"); }
    public double r() { return getDouble("r"); }
    public double rx() { return getDouble("rx"); }
    public double ry() { return getDouble("ry"); }
    public double fillOpacity() { return getDouble("fill-opacity"); }

    public JSONObject object(String key) { return optJSONObject(key); }
    public JSONArray array(String key) { return optJSONArray(key); }
    public double D(String key) { return getDouble(key); }
    public int I(String key) { return getInt(key); }
    public long L(String key) { return getLong(key); }
    public String string(String key) { return getString(key); }

    public static Option opt() { return new Option(); }

}
