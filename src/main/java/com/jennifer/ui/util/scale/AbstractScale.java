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

import com.jennifer.ui.util.JSONUtil;
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

    public double max() {
        return Math.max(domain.getDouble(0), domain.getDouble(domain.length()-1));
    }

    public double min() {
        return Math.min(domain.getDouble(0), domain.getDouble(domain.length() - 1));
    }

    public double rangeBand() {
        return _rangeBand;
    }

    public double rate(double value, double max) {
        return get(max() * (value / max));
    }

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
