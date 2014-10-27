package com.jennifer.ui.util;

import org.json.JSONArray;

import javax.print.attribute.standard.OrientationRequested;
import java.util.ArrayList;

/**
 * Created by Jayden on 2014-10-24.
 */
public class OrdinalScale extends AbstractScale {

    public OrdinalScale() {
        super();
    }

    public OrdinalScale(JSONArray domain, JSONArray range) {
        super(domain, range);
    }

    public double get(String x) {
        int index = -1;

        JSONArray domain = domain();

        for (int i = 0, len = domain.length(); i < len; i++) {
            if (x.equals(domain.getString(i))) {
                index = i;
                break;
            }
        }

        if (index > -1) {
            return range().getDouble(index);
        } else {
            return -1;
        }
    }

    public OrdinalScale rangePoints(double interval[], double padding) {

        JSONArray domain = domain();
        JSONArray range = new JSONArray();

        int step = domain.length();
        double unit = (interval[1] - interval[0] - padding) / step;

        for(int i = 0, len = domain.length(); i < len; i++) {
            if (i == 0) {
                range.put(Double.valueOf(interval[0] + padding/2 + unit/2));
            } else {
                range.put(range.getDouble(i-1) + unit);
            }
        }

        this.range(range);
        _rangeBand = unit;

        return this;
    }

    public OrdinalScale rangeBands(double interval[], double padding, double outerPadding) {

        JSONArray domain = domain();
        JSONArray range = new JSONArray();

        int count = domain.length();
        int step = count - 1;
        double band = (interval[1] - interval[0]) / step;

        for(int i = 0, len = domain.length(); i < len; i++) {
            if (i == 0) {
                range.put(interval[0]);
            } else {
                range.put(band + range.getDouble(i - 1));
            }
        }

        this.range(range);
        _rangeBand = band;

        return this;
    }

}
