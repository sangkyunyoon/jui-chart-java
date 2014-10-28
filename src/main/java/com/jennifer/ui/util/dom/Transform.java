package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.DomUtil;
import com.jennifer.ui.util.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class Transform extends DomUtil {

    private JSONObject orders = new JSONObject();

    public Transform(String tagName, JSONObject attr) {
        super(tagName, attr);
    }

    public Transform(String tagName) {
        super(tagName);
    }

    public Transform translate(double x) {
        orders.put("translate", x);
        return this;
    }

    public Transform translate(double x, double y) {
        orders.put("translate", x + "," + y);
        return this;
    }


    public String render() {

        JSONArray list = new JSONArray();
        JSONArray names = orders.names();

        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);

                if (orders.has(key)) {
                    list.put(key + "(" + orders.getString(key) + ")");
                }
            }

            put("transform", JSONUtil.join(list, " "));

        }

        return super.render();
    }
}
