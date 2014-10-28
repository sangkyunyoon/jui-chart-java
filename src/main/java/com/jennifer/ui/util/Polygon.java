package com.jennifer.ui.util;

import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

public class Polygon extends Transform {

    private JSONArray orders = new JSONArray();

    public Polygon(JSONObject o) {
        super("polygon", o);
    }

    public Polygon() {
        super("polygon");
    }

    public Polygon point(double x, double y) {
        orders.put(x + "," + y);

        return this;
    }

    @Override
    public String render() {

        put("points", join(orders, "-"));

        return super.render();
    }
}
