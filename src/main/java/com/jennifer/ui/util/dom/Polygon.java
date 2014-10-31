package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;

public class Polygon extends Transform {

    private OptionArray orders = new OptionArray();

    public Polygon(Option o) {
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
