package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.DomUtil;
import com.jennifer.ui.util.JSONUtil;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import org.json.JSONArray;

/**
 * Created by Jayden on 2014-10-27.
 */
public class Transform extends DomUtil {

    private Option orders = new Option();

    public Transform(String tagName, Option attr) {
        super(tagName, attr);
    }

    public Transform(String tagName) {
        super(tagName);
    }

    public Transform translate(double x) {
        orders.put("translate", x);
        return this;
    }

    public Transform rotate(double angle) {
        orders.put("rotate", angle);
        return this;
    }

    public Transform translate(double x, double y) {
        orders.put("translate", x + " " + y);
        return this;
    }

    public Transform rotate(double angle, double x, double y) {
        orders.put("rotate", angle + " " + x + " " + y);
        return this;
    }

    public String render() {

        OptionArray list = new OptionArray();
        JSONArray names = orders.names();

        if (names != null) {
            for(int i = 0, len = names.length(); i < len; i++) {
                String key = names.getString(i);

                if (orders.has(key)) {
                    list.put(key + "(" + orders.string(key) + ")");
                }
            }

            put("transform", JSONUtil.join(list, " "));

        }

        return super.render();
    }

}
