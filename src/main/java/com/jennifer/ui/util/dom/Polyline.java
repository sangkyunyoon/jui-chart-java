package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;

public class Polyline extends Transform {
    private OptionArray orders = new OptionArray();

    public Polyline(Option o) {
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
