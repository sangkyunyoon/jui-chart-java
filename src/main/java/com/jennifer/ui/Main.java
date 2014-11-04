package com.jennifer.ui;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.*;

/**
 * Created by Jayden on 2014-10-24.
 */
public class Main {

    public static void main(String[] args) {
        String filename = "src/sample/" + args[0] + ".json";
        String saveFilename = "src/sample/" + args[0] + ".svg";
        ChartBuilder chart = new ChartBuilder(new Option(JSONUtil.readFile(filename)));
        chart.writeFile(saveFilename);
    }
}
