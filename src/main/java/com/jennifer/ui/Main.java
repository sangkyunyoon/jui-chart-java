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

        String json = "{\n" +
                "  \"brush\": {\n" +
                "    \"colors\": [\n" +
                "      \"#773ab6\",\n" +
                "      \"#7bbae7\",\n" +
                "      \"#1a345b\",\n" +
                "      \"#b3d75a\",\n" +
                "      \"#3f7cf4\",\n" +
                "      \"#f0c7f3\",\n" +
                "      \"#2ec2ba\",\n" +
                "      \"#584f42\",\n" +
                "      \"#ababab\",\n" +
                "      \"#badbac\"\n" +
                "    ],\n" +
                "    \"type\": \"pie\"\n" +
                "  },\n" +
                "  \"data\": [{\n" +
                "    \"10003\": 17.007730147575543,\n" +
                "    \"10004\": 12.785464709993011\n" +
                "  }],\n" +
                "  \"grid\": {},\n" +
                "  \"height\": 220,\n" +
                "  \"padding\": {\n" +
                "    \"bottom\": 20,\n" +
                "    \"right\": 240,\n" +
                "    \"top\": 40\n" +
                "  },\n" +
                "  \"series\": {\n" +
                "    \"10003\": {\"text\": \"10003(17.007730147575543)\"},\n" +
                "    \"10004\": {\"text\": \"10004(12.785464709993011)\"}\n" +
                "  },\n" +
                "  \"widget\": [\n" +
                "    {\n" +
                "      \"text\": \"\",\n" +
                "      \"type\": \"title\"\n" +
                "    },\n" +
                "    {\"type\": \"legend\", brush : [0], position : 'right' }\n" +
                "  ],\n" +
                "  \"width\": 756\n" +
                "}";
        ChartBuilder chart = new ChartBuilder(json);


            System.out.println(chart.render());
            //chart.writeFile("line.html");


        /**
         * 작업하던거 넣고
         *
         */


    }
}
