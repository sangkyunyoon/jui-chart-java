package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class ScatterPathBrush extends Brush {


    public ScatterPathBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public ScatterPathBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {

    }

    @Override
    public Object draw() {
        return null;
    }
}
