package com.jennifer.ui.chart;

import com.jennifer.ui.util.DomUtil;

import java.util.HashMap;
import java.util.List;

/**
 *
 *
 *
 *
 * Created by yuni on 2014-10-23.
 */
public abstract  class AbstractDraw implements Drawable  {

    public abstract void drawBefore();
    public abstract Object draw();

    public Object render() {
        this.drawBefore();

        return this.draw();
    }

}
