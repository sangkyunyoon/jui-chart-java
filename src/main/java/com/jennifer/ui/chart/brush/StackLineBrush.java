package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class StackLineBrush extends LineBrush {

    public StackLineBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public StackLineBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }
}
