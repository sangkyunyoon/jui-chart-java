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

package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;

public class Path extends Transform {
    private OptionArray orders = new OptionArray();

    public Path(Option o) {
        super("path", o);
    }

    public Path() {
        super("path");
    }

    public Path moveTo(double x, double y) { orders.put("m" + x + "," + y); return this; }
    public Path MoveTo(double x, double y) { orders.put("M" + x + "," + y); return this; }
    public Path lineTo(double x, double y) { orders.put("l" + x + "," + y); return this; }
    public Path LineTo(double x, double y) { orders.put("L" + x + "," + y); return this; }
    public Path hLineTo(double x) { orders.put("h" + x ); return this; }
    public Path HLineTo(double x) { orders.put("H" + x ); return this; }
    public Path vLineTo(double x) { orders.put("v" + x ); return this; }
    public Path VLineTo(double x) { orders.put("V" + x ); return this; }
    public Path curveTo(double x1, double y1, double x2, double y2, double x, double y) { orders.put("c" + x1 + "," + y1 + " " + x2 + "," + y2 + " " + x + "," + y); return this; }
    public Path CurveTo(double x1, double y1, double x2, double y2, double x, double y) { orders.put("C" + x1 + "," + y1 + " " + x2 + "," + y2 + " " + x + "," + y); return this; }
    public Path sCurveTo(double x2, double y2, double x, double y) { orders.put("s" + x2 + "," + y2 + " " + x + "," + y); return this; }
    public Path SCurveTo(double x2, double y2, double x, double y) { orders.put("S" + x2 + "," + y2 + " " + x + "," + y); return this; }
    public Path qCurveTo(double x1, double y1, double x, double y) { orders.put("q" + x1 + "," + y1 + " " + x + "," + y); return this; }
    public Path QCurveTo(double x1, double y1, double x, double y) { orders.put("Q" + x1 + "," + y1 + " " + x + "," + y); return this; }
    public Path tCurveTo(double x1, double y1, double x, double y) { orders.put("t" + x1 + "," + y1 + " " + x + "," + y); return this; }
    public Path TCurveTo(double x1, double y1, double x, double y) { orders.put("T" + x1 + "," + y1 + " " + x + "," + y); return this; }
    public Path arc(double rx, double ry, double x_axis_rotation, int large_arc_flag, int sweep_flag, double x, double y) {
        orders.put("a" + rx + "," + ry + " " + x_axis_rotation + " " + large_arc_flag + "," + sweep_flag + " " + x + "," + y );
        return this;
    }

    public Path Arc(double rx, double ry, double x_axis_rotation, int large_arc_flag, int sweep_flag, double x, double y) {
        orders.put("A" + rx + "," + ry + " " + x_axis_rotation + " " + large_arc_flag + "," + sweep_flag + " " + x + "," + y );
        return this;
    }

    public Path close() { orders.put("z"); return this;   }
    public Path Close() { orders.put("Z"); return this;   }

    /**
     * create symbol in path
     *
     */
    public Path triangle(double cx, double cy, double width, double height) {
        return MoveTo(cx, cy).moveTo(0, -height/2).lineTo(width/2,height).lineTo(-width, 0).lineTo(width/2, -height);
    }

    public Path rect (double cx, double cy, double width, double height) {
        return MoveTo(cx, cy).moveTo(-width/2, -height/2).lineTo(width,0).lineTo(0, height).lineTo(-width, 0).lineTo(0, -height);
    }

    public Path rectangle (double cx, double cy, double width, double height) {
        return rect(cx, cy, width, height);
    }

    public Path cross(double cx, double cy, double width, double height) {
        return MoveTo(cx, cy).moveTo(-width/2, -height/2).lineTo(width, height).moveTo(0, -height).lineTo(-width, height);
    }

    public Path circle(double cx, double cy, double r) {
        return MoveTo(cx, cy).moveTo(-r, 0).arc(r, r, 0, 1, 1, r*2, 0).arc(r, r, 0, 1, 1, -(r*2), 0);
    }

    @Override
    public String render() {

        put("d", join(orders, " "));

        return super.render();
    }

    public String render(int tabIndex, int tabSize) {

        put("d", join(orders, " "));

        return super.render(tabIndex, tabSize);
    }
}
