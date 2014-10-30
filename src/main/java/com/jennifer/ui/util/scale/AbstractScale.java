package com.jennifer.ui.util.scale;

import org.json.JSONArray;

/**
 * Created by yuni on 2014-10-24.
 */
public abstract class AbstractScale implements Scale {
    protected double _rangeBand;
    protected boolean _clamp;
    private JSONArray domain = new JSONArray();
    private JSONArray range = new JSONArray();
    private String key;

    protected AbstractScale() {
        init();
    }

    protected AbstractScale(JSONArray domain, JSONArray range) {
        this.domain = domain;
        this.range = range;

        init();
    }

    protected void init() {


    }

    public void clamp(boolean clamp) {
        _clamp = clamp;
    }

    public double get(double x) {
        return 0;
    }

    public double get(String x) {
        return 0;
    }

    @Override
    public double max() {
        return Math.max(domain.getDouble(0), domain.getDouble(domain.length()-1));
    }

    @Override
    public double min() {
        return Math.min(domain.getDouble(0), domain.getDouble(domain.length() - 1));
    }

    @Override
    public double rangeBand() {
        return _rangeBand;
    }

    @Override
    public double rate(double value, double max) {
        return get(max() * (value / max));
    }

    @Override
    public double invert(double y) {
        return 0;
    }

    public JSONArray domain() {
        return domain;
    }

    public JSONArray range() {
        return range;
    }

    public Scale domain(JSONArray domain) {
        this.domain = domain;
        return this;
    }

    public Scale range(JSONArray range) {
        this.range = range;
        return this;
    }

    public Scale setKey(String key) {
        this.key = key;
        return this;
    }

    public Scale rangeBands(JSONArray interval, int i, int i1) {
        return this;
    }

    public Scale rangePoints(JSONArray interval, int i) {
        return this;
    }

    public Scale rangeRound(JSONArray range) {
        return this;
    }
}
