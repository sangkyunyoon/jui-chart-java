package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class ColumnBrush extends Brush {
    public ColumnBrush(ChartBuilder chart, JSONObject options) {
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
