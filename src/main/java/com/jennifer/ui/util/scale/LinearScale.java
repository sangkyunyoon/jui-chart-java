package com.jennifer.ui.util.scale;

import com.jennifer.ui.util.MathUtil;
import org.json.JSONArray;

/**
 * Linear Scale
 *
 * new LinearScale(domain, range)
 *
 *
 *
 * Created by yuni on 2014-10-24.
 */
public class LinearScale extends AbstractScale {
    private boolean _round = false ;
    public LinearScale() {
        super();
    }

    public LinearScale(JSONArray domain, JSONArray range) {
        super(domain, range);
    }

    public double get(double x) {
        int index = -1;
        int target;

        JSONArray domain = domain();
        
        for (int i = 0, len = domain.length(); i < len; i++) {

            if (i == len - 1) {
                if (x == domain.getDouble(i)) {
                    index = i;
                    break;
                }
            } else {
                if (domain.getDouble(i) < domain.getDouble(i + 1)) {
                    if (x >= domain.getDouble(i) && x < domain.getDouble(i + 1)) {
                        index = i;
                        break;
                    }
                } else if (domain.getDouble(i) >= domain.getDouble(i + 1)) {
                    if (x <= domain.getDouble(i) && domain.getDouble(i + 1) < x) {
                        index = i;
                        break;
                    }
                }
            }
        }

        JSONArray range = range();

        if (range.length() == 0) {
            if (index == 0) {
                return 0;
            } else if (index == -1) {
                return 1;
            } else {
                double min = domain.getDouble(index - 1);
                double max = domain.getDouble(index);

                double pos = (x - min) / (max - min);

                return pos;
            }
        } else {

            if (domain.length() - 1 == index) {
                return range.getDouble(index);
            } else if (index == -1) {

                double max = max();
                double min = min();

                if (max < x) {

                    if (_clamp) return max;

                    double last = domain.getDouble(domain.length() - 1);
                    double last2 = domain.getDouble(domain.length() -2);

                    double rlast = range.getDouble(range.length() -1);
                    double rlast2 = range.getDouble(range.length() -2);

                    double distLast = Math.abs(last - last2);
                    double distRLast = Math.abs(rlast - rlast2);

                    return rlast + Math.abs(x - max) * distRLast / distLast;

                } else if (min > x) {

                    if (_clamp) return min;

                    double first = domain.getDouble(0);
                    double first2 = domain.getDouble(1);

                    double rfirst = range.getDouble(0);
                    double rfirst2 = range.getDouble(1);

                    double distFirst = Math.abs(first - first2);
                    double distRFirst = Math.abs(rfirst - rfirst2);

                    return rfirst - Math.abs(x - min) * distRFirst / distFirst;
                }

                return range.getDouble(range.length() - 1);
            } else {

                double min = domain.getDouble(index);
                double max = domain.getDouble(index+1);

                double minR = range.getDouble(index);
                double maxR = range.getDouble(index + 1);

                double pos = (x - min) / (max - min);

                double scale = _round ? MathUtil.interpolateRound(minR, maxR, pos) : MathUtil.interpolateNumber(minR, maxR, pos);

                return scale;

            }
        }

    }

    public LinearScale rangeRound(JSONArray range) {
        _round = true;

        return (LinearScale)this.range(range);
    }

    public double invert(double y) {
        return new LinearScale(this.range(), this.domain()).get(y);
    }

    public JSONArray ticks() {
        return ticks(10, false, 10000000);
    }

    public JSONArray ticks(int count, boolean isNice) {
        return ticks(count, isNice, 10000000);
    }

    public JSONArray ticks(int count, boolean isNice, int intNumber) {
        JSONArray list = new JSONArray();

        JSONArray domain = domain();

        if (domain.getDouble(0) == 0 && domain.getDouble(1) == 0) {
            return new JSONArray();
        }

        JSONArray arr = MathUtil.nice(domain.getDouble(0), domain.getDouble(1), count, isNice );

        double min = arr.getDouble(0);
        double max = arr.getDouble(1);
        double range = arr.getDouble(2);
        double spacing = arr.getDouble(3);

        double start = min * intNumber;
        double end = max * intNumber;
        while (start <= end) {
            list.put(start/intNumber);
            start += spacing * intNumber;
        }

        if (list.getDouble(list.length()-1) * intNumber != end && start > end) {
            list.put(end / intNumber);
        }

        return list;
    }
}
