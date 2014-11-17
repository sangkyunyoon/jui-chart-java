package com.jennifer.ui;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.*;

/**
 * Created by Jayden on 2014-10-24.
 */
public class Main {

    public static void main(String[] args) {
        String filename = "/resources/" + args[0] + ".json";
        String saveFilename = "/resources/" + args[0] + ".svg";
        ChartBuilder chart = new ChartBuilder(new Option(JSONUtil.readFile(filename)));
        System.out.println(chart.render());
    }
}
