package com.jennifer.ui;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        String json = "{\"width\":756,\"height\":220,\"padding\":{\"top\":40},\"data\":[{\"start\":1418569200000,\"end\":1418655540000,\"75539_$sum\":168,\"75540_$sum\":166,\"$sum\":334},{\"start\":1418655600000,\"end\":1418741940000,\"75539_$sum\":374,\"75540_$sum\":373,\"$sum\":747},{\"start\":1418742000000,\"end\":1418828340000,\"75539_$sum\":299,\"75540_$sum\":297,\"$sum\":596},{\"start\":1418828400000,\"end\":1418914740000,\"75539_$sum\":272,\"75540_$sum\":273,\"$sum\":545},{\"start\":1418914800000,\"end\":1419001140000,\"75539_$sum\":376,\"75540_$sum\":375,\"$sum\":751},{\"start\":1419001200000,\"end\":1419087540000,\"75539_$sum\":309,\"75540_$sum\":312,\"$sum\":621},{\"start\":1419087600000,\"end\":1419173940000,\"75539_$sum\":241,\"75540_$sum\":239,\"$sum\":480}],\"grid\":{\"x\":{\"type\":\"block\",\"target\":\"start\",\"line\":true,\"format\":\"com.jennifersoft.view.template.TemplateChartUtil$3@3d216f5a\"},\"y\":{\"type\":\"range\",\"target\":\"$sum\",\"unit\":\"com.jennifersoft.view.template.TemplateChartUtil$4@5e159d10\"}},\"brush\":{\"type\":\"column\",\"target\":\"$sum\",\"colors\":[\"#19345b\",\"#5b3299\",\"#3f7cf4\",\"#47b2ac\",\"#badbac\",\"#3f5ca8\",\"#a9d8f8\",\"#ffc000\",\"#555d69\",\"#64b044\"]},\"widget\":{\"type\":\"title\",\"text\":\"bar sample - metrics \"}}";
        ChartBuilder chart = new ChartBuilder(new Option(json));

        chart.set("grid.x.format", new ChartDateFormat() {
                    public String format(long d) {
                        return new SimpleDateFormat("yy-MM-dd").format(new Date(d));
                    }
                });

        chart.set("grid.y.unit", new ChartUnit() {

            public double getUnit(double max, double size) {
                return 100;
            }
        });

            //System.out.println(chart.render());
            chart.writeFile("line.html");


        /**
         * 작업하던거 넣고
         *
         */


    }
}
