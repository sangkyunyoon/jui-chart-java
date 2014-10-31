package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class StackGagueBrush extends DonutBrush {

    public StackGagueBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public StackGagueBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }
}
