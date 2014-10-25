package com.jennifer.ui.chart;

import com.jennifer.ui.util.DomUtil;

import java.util.HashMap;
import java.util.List;

/**
 *
 *
 *
 *
 * Created by yuni on 2014-10-23.
 */
public abstract  class AbstractDraw {


    HashMap<String, Object> attrs = new HashMap<String, Object>();

    public abstract void drawBefore();
    public abstract Object draw();

    public void attr(String key, Object value) {
        attrs.put(key, value);
    }

    public Object attr(String key) {
        return attrs.get(key);
    }

    public HashMap<String, Object> attr() {
        return attrs;
    }

    public boolean has(String key) {
        return attrs.containsKey(key);
    }

    public int i(String key) {
        return Integer.valueOf(s(key)).intValue();
    }
    public int i(String key, int defaultValue) {

        if (has(key)) {
            return Integer.valueOf(s(key)).intValue();
        } else {
            return defaultValue;
        }
    }

    public long l(String key) {
        return Long.valueOf(s(key)).longValue();
    }

    public double d(String key) {
        return Double.valueOf(s(key)).doubleValue();
    }

    public boolean b(String key) {
        return Boolean.valueOf(s(key)).booleanValue();
    }

    public String s(String key) {
        return (String)attrs.get(key);
    }

    public List<Object> list(String key) {
        return (List<Object>)attrs.get(key);
    }

    public Object render() {

        this.drawBefore();

        return this.draw();
    }

    protected DomUtil dom(String tagName) {
        return DomUtil.el(tagName);
    }

    protected DomUtil dom(String tagName, String[] attrs) {
        return DomUtil.el(tagName, attrs);
    }

    /**
     * Common Attributes
     *
     * @return
     */
    public String target() {
        return s("target");
    }
}