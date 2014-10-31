package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-27.
 */
public class RadarGrid extends Grid{
    public RadarGrid(Orient orient, ChartBuilder chart, Option options) {
        super(orient, chart, options);
    }

    public RadarGrid(Orient orient, ChartBuilder chart, JSONObject options) {
        super(orient, chart, options);
    }
}
