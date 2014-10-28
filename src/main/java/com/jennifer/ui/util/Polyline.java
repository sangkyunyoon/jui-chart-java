package com.jennifer.ui.util;

import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

public class Polyline extends Transform {
    private JSONArray orders = new JSONArray();

    public Polyline(JSONObject o) {
        super("polyline", o);
    }

    public Polyline() {
        super("polyline");
    }

    public Polyline point(double x, double y) {
        orders.put(x + "," + y);

        return this;
    }

    @Override
    public String render() {

        put("points", join(orders, "-"));

        return super.render();
    }
}
