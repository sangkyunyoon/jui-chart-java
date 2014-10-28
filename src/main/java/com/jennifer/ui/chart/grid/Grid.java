package com.jennifer.ui.chart.grid;

import com.jennifer.ui.chart.AbstractDraw;
import com.jennifer.ui.chart.ChartBuilder;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jayden on 2014-10-24.
 */
public class Grid extends AbstractDraw {
    protected Orient orient;
    protected ChartBuilder chart;
    protected JSONObject options;

    public Grid(Orient orient, ChartBuilder chart, JSONObject options) {
        this.chart = chart;
        this.orient = orient;

        init();
    }

    public void init() {

    }

    @Override
    public void drawBefore() {

    }

    @Override
    public Object draw() {
        return null;
    }

    public Object drawGrid() {

        return null;
    }
}