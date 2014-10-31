package com.jennifer.ui.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jennifer.ui.util.Option.opt;

/**
 * Created by yuni on 2014-10-26.
 */
public class ColorUtil {

    public static final String GRADIENT_LINEAR = "linear";
    public static final String GRADIENT_RADIAL = "radial";

    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String TOP_LEFT = "top left";
    public static final String TOP_RIGHT = "top right";
    public static final String BOTTOM_RIGHT = "bottom right";
    public static final String BOTTOM_LEFT = "bottom left";
    public static final String SPLITTER = "-";
    public static final String STOP_SPLITTER = " ";
    public static final String DIRECTION_SPLITTER = " ";

    private static String regex = "(linear|radial)\\((.*)\\)(.*)";

    public static Object parse(String color) {
        return parsedGradient(color);
    }

    private static Object parsedGradient(String color) {

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(color);

        if (!m.matches()) return color;

        String type = m.group(1).trim();
        Option attr = parseAttr(type, m.group(2).trim());
        OptionArray stops = parseStop(m.group(3).trim());

        Option o = new Option();
        o.put("type", type);

        o = JSONUtil.extend(o, attr);

        o.put("stops", stops);


        return o;
    }

    private static OptionArray parseStop(String str) {
        OptionArray list = new OptionArray();
        String[] stop_list = str.split(SPLITTER);
        for(String stop : stop_list) {
            String[] arr = stop.trim().split(STOP_SPLITTER);

            if (arr.length == 0) continue;

            Option o = opt();
            list.put(o);
            if (arr.length == 1) {
                o.put("stop-color", arr[0]);
            } else if (arr.length == 2) {
                o.put("offset", arr[0]).put("stop-color", arr[1]);
            } else if (arr.length == 3) {
                o.put("offset", arr[0]).put("stop-color", arr[1]).put("stop-opacity", arr[2]);
            }
        }

        int start = -1;
        int end = -1;
        for(int i = 0, len = list.length(); i < len; i++) {
            Option stop = (Option) list.object(i);

            if (i == 0) {
                if (!stop.has("offset")) stop.put("offset", 0);
            } else if (i == len - 1) {
                if (!stop.has("offset")) stop.put("offset", 1);
            }

            if (start == -1 && !stop.has("offset")) {
                start = i;
            } else if (end == -1 && !stop.has("offset")) {
                end = i;

                int count = end - start;

                double endOffset = list.object(end).getDouble("offset");
                double startOffset = list.object(start).getDouble("offset");

                double dist  = endOffset - startOffset;
                double value = dist / count;

                double offset = startOffset + value;
                for(int index = start + 1; index < end; index++) {
                    list.object(index).put("offset", offset);

                    offset += value;
                }

                start = end;
                end = -1;
            }
        }

        return list;
    }

    private static Option parseAttr(String type, String direction) {
        Option o = opt();

        if (GRADIENT_LINEAR.equals(type)) {
             if ("".equals(direction)) {
                 direction = LEFT;
             }

            o.put("direction", direction);

            if (LEFT.equals(direction)) {
                o.put("x1", 0).put("y1", 0).put("x2", 1).put("y2", 0);
            } else if (RIGHT.equals(direction)) {
                o.put("x1", 1).put("y1", 0).put("x2", 0).put("y2", 0);
            } else if (TOP.equals(direction)) {
                o.put("x1", 0).put("y1", 0).put("x2", 0).put("y2", 1);
            } else if (BOTTOM.equals(direction)) {
                o.put("x1", 0).put("y1", 1).put("x2", 0).put("y2", 0);
            } else if (TOP_LEFT.equals(direction)) {
                o.put("x1", 0).put("y1", 0).put("x2", 1).put("y2", 1);
            } else if (TOP_RIGHT.equals(direction)) {
                o.put("x1", 1).put("y1", 0).put("x2", 0).put("y2", 1);
            } else if (BOTTOM_LEFT.equals(direction)) {
                o.put("x1", 0).put("y1", 1).put("x2", 1).put("y2", 0);
            } else if (BOTTOM_RIGHT.equals(direction)) {
                o.put("x1", 1).put("y1", 1).put("x2", 0).put("y2", 0);
            } else {
                String[] arr = direction.split(DIRECTION_SPLITTER);

                if (arr.length == 4) {
                    OptionArray list = new OptionArray();
                    for(int i = 0, len = arr.length; i < len; i++) {
                        list.put(i, arr[i]);
                    }

                    o.put("x1", list.D(0)).put("y1", list.D(1)).put("x2", list.D(2)).put("y2", list.D(3));
                }
            }
        } else {
            String[] arr = direction.split(DIRECTION_SPLITTER);
            OptionArray list = new OptionArray();
            for(int i = 0, len = arr.length; i < len; i++) {
                list.put(i, arr[i]);
            }

            o.put("cx", list.D(0)).put("cy", list.D(1)).put("r", list.D(2)).put("fx", list.D(3)).put("fy", list.D(4));
        }

        return o;
    }

    public static void main(String args[]) {
        System.out.println(parse("linear(left) red-blue"));
        System.out.println(parse("linear(right) red-blue"));
        System.out.println(parse("linear(bottom) red-blue"));
        System.out.println(parse("linear(top) red-blue"));
        System.out.println(parse("linear(top left) red-blue"));
        System.out.println(parse("linear(bottom left) red-blue"));
        System.out.println(parse("linear(top right) red-blue"));
        System.out.println(parse("linear(bottom right) red-0.5 blue-1 red"));
        System.out.println(parse("radial(10 10 5 10 10) red-0.5 blue-1 red"));
    }
}
