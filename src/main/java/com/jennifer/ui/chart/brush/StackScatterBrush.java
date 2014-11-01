package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class StackScatterBrush extends ScatterBrush {


    public StackScatterBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public StackScatterBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    public Object draw() {
        return drawScatter(getStackXY());
    }
}
