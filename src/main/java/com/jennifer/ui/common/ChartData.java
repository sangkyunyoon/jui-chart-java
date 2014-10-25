package com.jennifer.ui.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuni on 2014-10-25.
 */
public class ChartData {

    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

    public ChartData() {

    }

    public int length() {
        return list.size();
    }

    public void append(String key, Object value) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put(key, value);
        list.add(map);
    }

    public void append(HashMap<String, Object> attrs) {
        list.add(attrs);
    }

    public void append(int i, String key, Object value) {
        HashMap<String, Object> attrs = list.get(i);

        if (attrs == null) {
            attrs = new HashMap<String, Object>();
            attrs.put(key, value);
            list.add(i, attrs);
        } else {
            attrs.put(key, value);
        }
    }

    public Object get(int i, String key) {
        HashMap<String, Object> map = list.get(i);

        if (map == null) return null;

        return map.get(key);
    }
}
