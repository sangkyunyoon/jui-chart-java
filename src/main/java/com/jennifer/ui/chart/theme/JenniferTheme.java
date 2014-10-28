package com.jennifer.ui.chart.theme;

import com.jennifer.ui.util.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-28.
 */
public class JenniferTheme {
    public static JSONObject create() {

        JSONArray themeColors = JSONUtil.toJSONArray(new String[] {
                "#7977C2",
                "#7BBAE7",
                "#FFC000",
                "#FF7800",
                "#87BB66",
                "#1DA8A0",
                "#929292",
                "#555D69",
                "#0298D5",
                "#FA5559",
                "#F5A397",
                "#06D9B6",
                "#C6A9D9",
                "#6E6AFC",
                "#E3E766",
                "#C57BC3",
                "#DF328B",
                "#96D7EB",
                "#839CB5",
                "#9228E4"
        });

        JSONObject o = new JSONObject();
        // common styles
        o.put("backgroundColor","white");
        o.put("fontSize","11px");
        o.put("fontColor","#333333");
        o.put("fontFamily", "arial,Tahoma,verdana");
        o.put("colors", themeColors);
                // grid styles
        o.put("gridFontColor","#333333");
        o.put("gridActiveFontColor","#ff7800");
        o.put("gridBorderWidth", "1");
        o.put("gridBorderColor","#ececec");
        o.put("gridAxisBorderColor","#ebebeb");
        o.put("gridAxisBorderWidth", "1");
        o.put("gridActiveBorderColor","#ff7800");
        o.put("gridActiveBorderWidth", "1");

        return o;
    }
}
