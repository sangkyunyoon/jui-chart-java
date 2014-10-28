package com.jennifer.ui.util;

import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Polygon;
import com.jennifer.ui.util.dom.Polyline;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-24.
 */
public class DomUtil {

    private JSONObject attrs = new JSONObject();;
    private String tagName;
    private String text = "";
    private JSONArray children = new JSONArray();;

    public DomUtil(String tagName, JSONObject attr) {
        this(tagName);
        this.attrs = attr;

        init();
    }

    public void init() {

    }

    public static DomUtil el(String tagName) {
        return new DomUtil(tagName);
    }

    public static DomUtil el(String tagName, JSONObject attr) {
        return new DomUtil(tagName, attr);
    }

    public DomUtil(String tagName) {
        this.tagName = tagName;

        init();
    }

    public DomUtil put(String key, int value) { attrs.put(key, value);  return this; }
    public DomUtil put(String key, double value) { attrs.put(key, value);  return this; }
    public DomUtil put(String key, Object value) { attrs.put(key, value);  return this; }
    public DomUtil put(String key, String value) { attrs.put(key, value);  return this; }

    public String getString(String key) { return attrs.getString(key); }
    public Object get(String key) { return attrs.get(key); }
    public int getInt(String key) { return attrs.getInt(key); }
    public double getDouble(String key) { return attrs.getDouble(key); }


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

    public String collapseAttr() {
        StringBuilder str = new StringBuilder();
        JSONArray names = attrs.names();

        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);
                String value = getString(key);

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
        StringBuffer str = new StringBuffer();

        str.append("<" + tagName );
        str.append(collapseAttr());
        if (children.length() == 0) {

            if (text.equals("")) {
                str.append(">" + text);
                str.append("</" + tagName + ">");
            } else {
                str.append(" />");
            }

        } else {
            str.append(">\n");
            str.append(collapseChildren());
            str.append(text);
            str.append("</" + tagName + ">");
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

}
