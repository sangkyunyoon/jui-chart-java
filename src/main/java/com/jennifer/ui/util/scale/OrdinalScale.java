package com.jennifer.ui.util.scale;

import com.jennifer.ui.util.OptionArray;

/**
 * Created by Jayden on 2014-10-24.
 */
public class OrdinalScale extends AbstractScale {

    public OrdinalScale() {
        super();
    }

    public OrdinalScale(OptionArray domain, OptionArray range) {
        super(domain, range);
    }

    public double get(String x) {
        int index = -1;

        OptionArray domain = domain();

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

    public double get(double x) {
        return range().getDouble((int)x);
    }

    public Scale rangePoints(OptionArray interval, int padding) {

        OptionArray domain = domain();
        OptionArray range = new OptionArray();

        double start = interval.getDouble(0);
        double end = interval.getDouble(1);

        int step = domain.length();
        double unit = (end - start - padding) / step;

        for(int i = 0, len = domain.length(); i < len; i++) {
            if (i == 0) {
                range.put(Double.valueOf(start + padding/2 + unit/2));
            } else {
                range.put(range.getDouble(i-1) + unit);
            }
        }

        this.range(range);
        _rangeBand = unit;
        return this;
    }

    public Scale rangeBands(OptionArray interval, int padding, int outerPadding) {

        OptionArray domain = domain();
        OptionArray range = new OptionArray();

        int count = domain.length();
        int step = count - 1;

        double start = interval.getDouble(0);
        double end = interval.getDouble(1);

        double band = (end - start) / step;

        for(int i = 0, len = domain.length(); i < len; i++) {
            if (i == 0) {
                range.put(start);
            } else {
                range.put(band + range.getDouble(i - 1));
            }
        }

        this.range(range);
        _rangeBand = band;

        return this;
    }

}
