package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class AreaBrush extends LineBrush {

    private Transform root;
    private int maxY;

    public AreaBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }
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
        OptionArray path = this.getXY();

        for(int k = 0, len = path.length(); k < len; k++) {
            Option o = (Option) path.object(k);

            Path p = createLine(o, k);
            OptionArray xList = (OptionArray) o.array("x");

            p.LineTo(xList.D(xList.length() - 1), maxY);
            p.LineTo(xList.D(0), maxY);
            p.Close();

            p.put("fill", color(k));
            p.put("fill-opacity", chart.theme("areaOpacity"));
            p.put("stroke-width", 0);


            root.append(p);

        }


        return opt().put("root", root);
    }


}
