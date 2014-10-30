package com.jennifer.ui.util;

import org.json.JSONObject;

/**
 * Created by yuni on 2014-10-31.
 */
public class Option extends JSONObject {
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
    public Option strokeWidth(String i) { return (Option)put("stroke-width", i); }
    public Option strokeOpacity(double o ) { return (Option)put("stroke-opacity", o); }
    public Option textAnchor(String anchor) { return (Option)put("text-anchore", anchor); }


}
