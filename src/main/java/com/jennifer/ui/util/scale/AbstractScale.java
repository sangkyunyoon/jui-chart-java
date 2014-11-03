/*
 * Copyright (C) 2014 (JenniferSoft Inc.)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
