package com.jennifer.ui;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.*;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Svg;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Jayden on 2014-10-24.
 */
public class Main {

    public static void main(String[] args) {
        /*
        LinearScale scale = new LinearScale();

        System.out.println(scale.get(0) + " : " + 0);
        System.out.println(scale.get(1) + " : " + 10);
        System.out.println(scale.get(100) + " : " + 1000);

        System.out.println(scale.invert(1000));
        System.out.println(scale.invert(100));
        System.out.println(scale.invert(10));
        System.out.println(scale.invert(0));

        System.out.println(scale.invert(99.9999));

        TimeScale tscale = new TimeScale();
        tscale.domain(new Date[] {
            new Date(),
            TimeUtil.add(new Date(), Time.HOURS, 5)
        });
        tscale.range( );

        System.out.println(tscale.get(TimeUtil.add(new Date(), Time.HOURS, 1).getTime()));

        System.out.println(tscale.ticks(Time.HOURS, 1));

        System.out.println(tscale.realTicks(Time.HOURS, 1));


        JSONObject o = new JSONObject();
        o.put("width", 100);
        o.put("height", 100);
        o.put("padding", new JSONObject().put("left", 0));
        o.put("padding", "empty");
        o.put("grid", new JSONObject()
                .put("x", new JSONObject()
                    .put("type", "block")
                    .put("target", "name")
                )
                .put("y", new JSONObject()
                    .put("type", "range")
                    .put("target", "name2")
                )
        );

        System.out.print(o.toString(4));
        */
        /** element test **/

        Svg svg = new Svg();

        Path path = svg.group().path();

        path.MoveTo(0, 0);
        path.LineTo(100, 100);
        path.Close();
        path.put("stroke", "black");
        path.put("stroke-dasharray", "5, 5");

        path.css("background", "yellow");


        //System.out.println(svg.toXml());


        JSONObject chartOpt = new JSONObject();
        chartOpt.put("theme", "dark");
        chartOpt.put("width", "800");
        chartOpt.put("height", "800");
        chartOpt.put("grid", new JSONObject());
        chartOpt.put("padding", new JSONObject());

        chartOpt.getJSONObject("padding").put("left", 100);


        JSONObject grid = chartOpt.getJSONObject("grid");
        grid.put("x", new JSONObject());

        JSONObject x = grid.getJSONObject("x");
        x.put("type", "date").put("target", "time").put("step", new JSONArray());
        x.getJSONArray("step").put("seconds").put(1);  // interval 1 minutes
        x.put("key", "time");

        grid.put("y", JSONUtil.clone(x));
        //grid.put("y1", JSONUtil.clone(x1));

        chartOpt.put("data", new JSONArray());
        JSONArray data = chartOpt.getJSONArray("data");

        long now = System.currentTimeMillis();

        for(int i = 0; i < 10; i++) {
            JSONObject d = new JSONObject();

            d.put("name", "tab" + i);
            d.put("value", i * 10);
            d.put("time", TimeUtil.add(now, "seconds", i));

            data.put(d);
        }

        //System.out.println(chartOpt.toString(4));

        ChartBuilder chart = new ChartBuilder(chartOpt);
        System.out.println(chart.render());
    }
}
