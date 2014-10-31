package com.jennifer.ui.util.scale;

import com.jennifer.ui.util.OptionArray;
import org.json.JSONArray;

/**
 * Created by yuni on 2014-10-24.
 */
public abstract class AbstractScale implements Scale {
    protected double _rangeBand;
    protected boolean _clamp;
    private OptionArray domain = new OptionArray();
    private OptionArray range = new OptionArray();
    private String key;

    protected AbstractScale() {
        init();
    }

    protected AbstractScale(OptionArray domain, OptionArray range) {
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

    public OptionArray domain() {
        return domain;
    }

    public OptionArray range() {
        return range;
    }

    public Scale domain(OptionArray domain) {
        this.domain = domain;
        return this;
    }

    public Scale domain(JSONArray domain) {
        this.domain = (OptionArray)domain;
        return this;
    }

    public Scale range(OptionArray range) {
        this.range = range;
        return this;
    }

    public Scale range(JSONArray range) {
        this.range = (OptionArray)range;
        return this;
    }

    public Scale setKey(String key) {
        this.key = key;
        return this;
    }

    public Scale rangeBands(OptionArray interval, int i, int i1) {
        return this;
    }

    public Scale rangePoints(OptionArray interval, int i) {
        return this;
    }

    public Scale rangeRound(OptionArray range) {
        return this;
    }
}
