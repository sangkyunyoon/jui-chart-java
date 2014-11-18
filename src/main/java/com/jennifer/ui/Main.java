package com.jennifer.ui;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.*;

/**
 * Created by Jayden on 2014-10-24.
 */
public class Main {

    public static void main(String[] args) {
        /*
        String filename = "resources/" + args[0] + ".json";
        String saveFilename = "resources/" + args[0] + ".svg";
        ChartBuilder chart = new ChartBuilder(new Option(JSONUtil.readFile(filename)));
        System.out.println(chart.render());
        */
        ChartBuilder chart = new ChartBuilder(800, 800);

        chart.grid("x", new Option().type("range").target("{value} + {value2}").step(10).line(true));
        chart.grid("y", new Option().type("block").line(true).put("target","name"));
        chart.widget(new Option().type("title").text("sample title"));
        chart.widget(new Option().type("legend").align("start"));
        chart.brush(new Option().type("stackbar").target(new OptionArray().put("value").put("value2")));

        long now = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            Option d = new Option();

            d.put("name", "tab" + i);
            d.put("value", i * 2 + 0.1);
            d.put("value2", i * 3 + 5);
            d.put("time", TimeUtil.add(now, "seconds", i));

            chart.add(d);
        }

        System.out.println(chart.render());

    }
}
