package com.jennifer.ui.chart;

import com.jennifer.ui.util.Option;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChartBuilderTest {
    @Test
    public void testJsonPath() {
        String json = "{ width : 300, height : 300 }";

        ChartBuilder builder = new ChartBuilder(json);

        assertEquals(builder.get("width").toString(), "300");
        assertEquals(builder.get("height").toString(), "300");


        builder.set("height", new Long(400));
        assertEquals(builder.get("height").toString(), "400");

        builder.set("grid", new JSONObject());
        builder.set("grid.x", new JSONObject());
        builder.set("grid.x.type", "range");

        assertEquals(builder.get("grid.x.type").toString(), "range");

        builder.set("grid.y", new JSONArray());
        builder.set("grid.y.0", new JSONObject());
        builder.set("grid.y.0.type", "block");

        assertEquals(builder.get("grid.y.0.type").toString(), "block");

        JSONArray list = (JSONArray)builder.get("grid.y");
        list.put(new JSONObject());
        list.getJSONObject(1).put("type", "date");

        assertEquals(builder.get("grid.y.1.type").toString(), "date");


    }
}