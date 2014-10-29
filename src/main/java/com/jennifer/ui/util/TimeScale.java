package com.jennifer.ui.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jayden on 2014-10-24.
 */
public class TimeScale extends LinearScale {

    public TimeScale() {
        super();
    }

    public TimeScale(JSONArray domain, JSONArray range) {
        super(domain, range);
    }


    public Scale domain(JSONArray domain) {

        for(int i = 0, len = domain.length(); i < len; i++) {
            if (domain.get(i) instanceof Date) {
                domain.put(i, ((Date)domain.get(i)).getTime());
            } else {
                domain.put(i, domain.get(i));
            }
        }

        return super.domain(domain);
    }

    public double get(Date d) {
        return get((double)d.getTime());
    }

    public double get(long time) {
        return get((double)time);
    }

    public long maxLong() {
        JSONArray domain = this.domain();
        return Math.max(domain.getLong(0), domain.getLong(domain.length()-1));
    }

    public long minLong() {
        JSONArray domain = this.domain();
        return Math.min(domain.getLong(0), domain.getLong(domain.length()-1));
    }

    public JSONArray ticks(String type, int step) {
        long start = this.minLong();
        long end = this.maxLong();

        JSONArray times = new JSONArray();

        while(start < end) {
            times.put(start);

            start = TimeUtil.add(start, type, step);
        }

        times.put(start);

        double first = this.get(times.getLong(0));
        double second = this.get(times.getLong(1));

        _rangeBand = second - first;

        return times;

    }

    public JSONArray realTicks(String type, int step) {
        long start = this.minLong();
        long end = this.maxLong();

        JSONArray times = new JSONArray();

        Calendar c = Calendar.getInstance();

        Date date = TimeUtil.get(start);
        Date realStart = null;

        c.setTime(date);
        if (Time.YEARS.equals(type)) {
            c.set(c.get(Calendar.YEAR), 1, 1, 0, 0, 0);
        } else if (Time.MONTHS.equals(type)) {
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
        } else if (Time.DAYS.equals(type)) {
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
        } else if (Time.HOURS.equals(type)) {
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), c.get(Calendar.HOUR_OF_DAY), 0, 0);
        } else if (Time.MINUTES.equals(type)) {
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0);
        } else if (Time.SECONDS.equals(type)) {
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
        }

        realStart = c.getTime();

        long start2 = realStart.getTime();
        while (start2 < end) {
            times.put(start2);

            start2 = TimeUtil.add(start2, type, step);
        }

        double first = this.get(times.getLong(1));
        double second = this.get(times.getLong(2));

        _rangeBand = second - first;

        return times;
    }
}
