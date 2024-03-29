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

package com.jennifer.ui.util;

import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Polygon;
import com.jennifer.ui.util.dom.Polyline;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jayden on 2014-10-24.
 */
public class DomUtil {

    private JSONObject attrs = new JSONObject();
    private JSONObject styles = new JSONObject();
    private String tagName;
    private String text = "";
    private JSONArray children = new JSONArray();

    public DomUtil(String tagName, JSONObject attr) {
        this(tagName);
        this.attrs = attr;

        init();
    }

    public void init() {

    }

    public static Transform el(String tagName) {
        return new Transform(tagName);
    }

    public static Transform el(String tagName, JSONObject attr) {
        return new Transform(tagName, attr);
    }

    public DomUtil(String tagName) {
        this.tagName = tagName;

        init();
    }

    public DomUtil children(int i) {
        return (DomUtil)children.get(i);
    }

    public DomUtil put(String key, int value) { attrs.put(key, value);  return this; }
    public DomUtil put(String key, double value) { attrs.put(key, value);  return this; }
    public DomUtil put(String key, Object value) { attrs.put(key, value);  return this; }
    public DomUtil put(String key, String value) { attrs.put(key, value);  return this; }

    public String getString(String key) { return attrs.getString(key); }
    public Object get(String key) { return attrs.get(key); }
    public int getInt(String key) { return attrs.getInt(key); }
    public double getDouble(String key) { return attrs.getDouble(key); }
    
    public DomUtil css(String key, int value) { styles.put(key, value);  return this; }
    public DomUtil css(String key, double value) { styles.put(key, value);  return this; }
    public DomUtil css(String key, Object value) { styles.put(key, value);  return this; }
    public DomUtil css(String key, String value) { styles.put(key, value);  return this; }

    public String cssString(String key) { return styles.getString(key); }
    public Object css(String key) { return styles.get(key); }
    public int cssInt(String key) { return styles.getInt(key); }
    public double cssDouble(String key) { return styles.getDouble(key); }

    public JSONObject css() {
        return styles;
    }

    public DomUtil css(JSONObject o) {

        String[] names  = JSONObject.getNames(o);

        for(String key : names){
            css(key, o.get(key));
        }

        return this;
    }

    public DomUtil append(String tagName) {
        return append(new DomUtil(tagName));
    }

    public DomUtil append(String tagName, JSONObject o) {
        return append(new DomUtil(tagName, o));
    }

    public DomUtil append(DomUtil dom) {
        children.put(dom);
        return dom;
    }

    public String textNode() {
        return text;
    }

    public DomUtil textNode(String text) {
        this.text = text;
        return this;
    }

    public String collapseStyle() {
        StringBuilder str = new StringBuilder();
        JSONArray names = styles.names();

        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);
                String value = styles.getString(key);

                str.append("" + key + ":" + value + ";");
            }
        }

        return str.toString();
    }

    public String collapseAttr() {
        StringBuilder str = new StringBuilder();

        String style = collapseStyle();

        if (!style.equals("")) {
            if (attrs.has("style")) {
                style =  getString("style") + ";" + style ;
            }
            put("style", style);
        }

        JSONArray names = attrs.names();

        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);
                String value = get(key).toString();
                str.append(" " + key + "=\"" + value + "\"");
            }
        }

        return str.toString();
    }

    public String collapseChildren() {
        StringBuffer str = new StringBuffer();

        for(int i = 0, len = children.length(); i < len; i++) {
            DomUtil dom = (DomUtil)children.get(i);
            str.append(dom.render() + "\n");
        }

        return str.toString();
    }

    public String render() {
        return render(0);
    }

    public String render(int tabIndex) {
        return render(tabIndex, 4);
    }

    public String render(int tabIndex, int tabSize) {

        String tab = "";

        for(int i = 0; i < tabIndex; i++) {
            tab += "\t";
        }

        StringBuffer str = new StringBuffer();

        str.append(tab + "<" + tagName );
        str.append(collapseAttr());
        if (children.length() == 0) {
            if (!text.equals("")) {
                str.append(">" + text);
                str.append("</" + tagName + ">");
            } else {
                str.append(" />");
            }

        } else {
            str.append(">\n");
            str.append(collapseChildren(tabIndex+1, tabSize));
            str.append(text);
            str.append(tab + "</" + tagName + ">");
        }

        return str.toString();
    }

    private String collapseChildren(int tabIndex, int tabSize) {
        StringBuffer str = new StringBuffer();

        for(int i = 0, len = children.length(); i < len; i++) {
            DomUtil dom = (DomUtil)children.opt(i);

            if (dom != null) {
                str.append(dom.render(tabIndex, tabSize) + "\n");
            }

        }

        return str.toString();
    }

    public String toString() {
        return render();
    }

    /**
     * util function
     */

    public DomUtil defs(JSONObject o) {
        return append(el("defs", o));
    }
    public DomUtil marker(JSONObject o) {
        return append(el("marker", o));
    }
    public DomUtil symbol(JSONObject o) {
        return append(el("symbol", o));
    }
    public DomUtil clipPath(JSONObject o) {
        return append(el("clipPath", o));
    }

    public Transform g(JSONObject o) {
        return (Transform)append(new Transform("g", o));
    }

    public Transform group(JSONObject o) {
        return g(o);
    }

    public Transform rect(JSONObject o) {
        return (Transform)append(new Transform("rect", o));
    }

    public Transform line(JSONObject o) {
        return (Transform)append(new Transform("line", o));
    }

    public Transform circle(JSONObject o) {
        return (Transform)append(new Transform("circle", o));
    }

    public Transform text(JSONObject o) {
        return (Transform)append(new Transform("text", o));
    }

    public Transform tspan(JSONObject o) {
        return (Transform)append(new Transform("tspan", o));
    }

    public Transform ellipse(JSONObject o) {
        return (Transform)append(new Transform("ellipse", o));
    }

    public Transform image(JSONObject o) {
        return (Transform)append(new Transform("image", o));
    }

    public Path path(JSONObject o) {
        return (Path)append(new Path(o));
    }

    public Polygon polygon(JSONObject o) {
        return (Polygon)append(new Polygon(o));
    }

    public Polyline polyline(JSONObject o) {
        return (Polyline)append(new Polyline(o));
    }


    /**
     * About Gradient
     *
     * @param o
     * @return
     */
    public DomUtil radialGradient(JSONObject o) {
        return append(el("radialGradient", o));
    }
    public DomUtil linearGradient(JSONObject o) {
        return append(el("linearGradient", o));
    }
    public DomUtil mask(JSONObject o) {
        return append(el("mask", o));
    }
    public DomUtil pattern(JSONObject o) {
        return append(el("pattern", o));
    }
    public DomUtil stop(JSONObject o) {
        return append(el("stop", o));
    }


    /**
     * About Animation Element
     *
     * @param {JSONObject} o
     * @return
     */
    public DomUtil animate (JSONObject o) {
        return append(el("animate", o));
    }
    public DomUtil animateColor (JSONObject o) {
        return append(el("animateColor", o));
    }
    public DomUtil animateMotion  (JSONObject o) {
        return append(el("animateMotion", o));
    }
    public DomUtil animateTransform (JSONObject o) {
        return append(el("animateTransform", o));
    }
    public DomUtil mpath (JSONObject o) {
        return append(el("mpath", o));
    }
    public DomUtil set (JSONObject o) {
        return append(el("set", o));
    }

    /**
     * util function
     */

    public DomUtil defs() {
        return append(el("defs"));
    }
    public DomUtil marker() {
        return append(el("marker"));
    }
    public DomUtil symbol() {
        return append(el("symbol"));
    }
    public DomUtil clipPath() {
        return append(el("clipPath"));
    }

    public Transform g() {
        return (Transform)append(new Transform("g"));
    }

    public Transform group() {
        return g();
    }

    public Transform rect() {
        return (Transform)append(new Transform("rect"));
    }

    public Transform line() {
        return (Transform)append(new Transform("line"));
    }

    public Transform circle() {
        return (Transform)append(new Transform("circle"));
    }

    public Transform text() {
        return (Transform)append(new Transform("text"));
    }

    public Transform tspan() {
        return (Transform)append(new Transform("tspan"));
    }

    public Transform ellipse() {
        return (Transform)append(new Transform("ellipse"));
    }

    public Transform image() {
        return (Transform)append(new Transform("image"));
    }

    public Path path() {
        return (Path)append(new Path());
    }

    public Polygon polygon() {
        return (Polygon)append(new Polygon());
    }

    public Polyline polyline() {
        return (Polyline)append(new Polyline());
    }


    /**
     * About Gradient
     *
     * @return DomUtil
     */
    public DomUtil radialGradient() {
        return append(el("radialGradient"));
    }
    public DomUtil linearGradient() {
        return append(el("linearGradient"));
    }
    public DomUtil mask() {
        return append(el("mask"));
    }
    public DomUtil pattern() {
        return append(el("pattern"));
    }
    public DomUtil stop() {
        return append(el("stop"));
    }


    public static String join(JSONArray list, String separator)  {
        int len = list.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i += 1) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(list.getString(i));
        }
        return sb.toString();
    }


    /**
     * About Animation Element
     *
     * @param {JSONObject} o
     * @return
     */
    public DomUtil animate () {
        return append(el("animate"));
    }
    public DomUtil animateColor () {
        return append(el("animateColor"));
    }
    public DomUtil animateMotion  () {
        return append(el("animateMotion"));
    }
    public DomUtil animateTransform () {
        return append(el("animateTransform"));
    }
    public DomUtil mpath () {
        return append(el("mpath"));
    }
    public DomUtil set () {
        return append(el("set"));
    }

    public DomUtil attr(JSONObject JSONObject) {
        JSONArray list = JSONObject.names();

        for(int i = 0, len = list.length(); i < len; i++) {
            String key = list.getString(i);
            put(key, JSONObject.get(key));
        }

        return this;
    }

    public DomUtil addClass(String s) {
        String[] list = attrs.optString("class").split(" ");
        String[] listEx = s.split(" ");
        ArrayList<String> result = new ArrayList<String>();

        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        for(String key : list) {
            map.put(key, new Boolean(true));
        }

        for(String key : listEx) {
            map.put(key, new Boolean(true));
        }

        for (String key : map.keySet()) {
            result.add(key);
        }

        put("class", StringUtil.join(result, " "));

        return this;

    }

    public DomUtil removeClass(String s) {
        String[] list = attrs.optString("class").split(" ");
        String[] listEx = s.split(" ");
        ArrayList<String> result = new ArrayList<String>();

        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        for(String key : list) {
            map.put(key, new Boolean(true));
        }

        for(String key : listEx) {
            map.remove(key);
        }

        for (String key : map.keySet()) {
            result.add(key);
        }

        put("class", StringUtil.join(result, " "));

        return this;

    }

    public boolean hasClass(String cls) {
        String[] list = attrs.optString("class").split(" ");

        for(String key : list) {
            if (key.equals(cls)) {
                return true;
            }
        }

        return false;
    }
}
