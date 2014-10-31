package com.jennifer.ui.util.scale;

import com.jennifer.ui.util.OptionArray;
import org.json.JSONArray;

/**
 * Created by yuni on 2014-10-24.
 */
public interface Scale {
    public Scale domain(JSONArray domain);
    public Scale domain(OptionArray domain);
    public Scale range(OptionArray range);
    public OptionArray domain();
    public OptionArray range();
    public double max();
    public double min();
    public double rangeBand();
    public double rate(double value, double max);
    public double invert(double y);
    public double get(double value);
    public double get(String x);
    public Scale rangeBands(OptionArray interval, int i, int i1);
    public Scale rangePoints(OptionArray interval, int i);
    public Scale rangeRound(OptionArray range);

}
