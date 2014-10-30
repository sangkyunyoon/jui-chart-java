package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;

/**
 * Created by Jayden on 2014-10-27.
 */
public class AreaBrush extends LineBrush {

    private Transform root;
    private int maxY;

    public AreaBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());
        maxY = chart.height();
    }

    @Override
    public Object draw() {
        JSONArray path = this.getXY();

        for(int k = 0, len = path.length(); k < len; k++) {
            JSONObject o = path.getJSONObject(k);

            Path p = this.createLine(o, k);
            JSONArray xList = o.getJSONArray("x");

            p.LineTo(xList.getDouble(xList.length() - 1), maxY);
            p.LineTo(xList.getDouble(0), maxY);
            p.Close();

            p.put("fill", chart.color(k, options.getJSONArray("colors")));
            p.put("fill-opacity", chart.theme("areaOpacity"));
            p.put("stroke-width", 0);


            root.append(p);

        }


        return new JSONObject().put("root", root);
    }


}
