package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class StackAreaBrush extends AreaBrush {

    public StackAreaBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public StackAreaBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }
}
