package com.jennifer.ui.chart;

import com.jennifer.ui.common.ChartData;

import java.util.List;
import java.util.Map;

/**
 * ChartBuilder options list
 *
 * width : px
 * height : px
 * padding : 0
 * paddingLeft : 0
 * paddingRight : 0
 * paddingTop : 0
 * paddingBottom : 0
 * theme
 * series
 * brush
 * widget
 * data
 *
 *
 * Created by yuni on 2014-10-23.
 */
public class ChartBuilder extends AbstractDraw {

    int width;
    int height;
    int paddingLeft;
    int paddingRight;
    int paddingTop;
    int paddingBottom;
    String theme = "jennifer";

    ChartData data = new ChartData();

    private Map<String, Series> series;
    List<Grid> x;
    List<Grid> y;
    List<Grid> x1;
    List<Grid> y1;
    //List<Brush> brush;
    //List<Widget> widget;
    //List<Data> data;

    public ChartBuilder() {}

    public ChartBuilder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ChartBuilder addSeries(Series s) {
        series.put(s.getTarget(), s);

        return this;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public ChartBuilder addGrid(Grid grid) {
        return this;
    }

    @Override
    public void drawBefore() {

    }

    @Override
    public Object draw() {
        return null;
    }

    public ChartData data() {
        return data;
    }
}
