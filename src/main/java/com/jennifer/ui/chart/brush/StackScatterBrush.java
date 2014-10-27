package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class StackScatterBrush extends ScatterBrush {
    public StackScatterBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    public Object draw() {
        return null;
        //return drawScatter(getStackXY());
    }
}
