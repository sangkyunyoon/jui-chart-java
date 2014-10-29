package com.jennifer.ui.util.scale;

import org.json.JSONArray;

/**
 * Created by yuni on 2014-10-24.
 */
public interface Scale {
    public Scale domain(JSONArray domain);
    public Scale range(JSONArray range);
    public JSONArray domain();
    public JSONArray range();
    public double max();
    public double min();
    public double rangeBand();
    public double rate(double value, double max);
    public double invert(double y);
    public double get(double value);
    public Scale rangeBands(JSONArray interval, int i, int i1);
    public Scale rangePoints(JSONArray interval, int i);
    public Scale rangeRound(JSONArray range);
}
