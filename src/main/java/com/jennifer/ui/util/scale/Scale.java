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
