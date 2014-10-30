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
public class LineBrush extends Brush {
    private Transform root;

    public LineBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());
    }



    @Override
    public Object draw() {

        JSONArray path = this.getXY();

        for(int k = 0, len = path.length(); k < len; k++) {
            root.append(createLine(path.getJSONObject(k), k));
        }


        return new JSONObject().put("root", root);
    }

    protected Path createLine(JSONObject pos, int index) {
        JSONArray x = pos.getJSONArray("x");
        JSONArray y = pos.getJSONArray("y");

        JSONObject attr = new JSONObject();
        attr.put("stroke", chart.color(index, options.getJSONArray("colors")));
        attr.put("stroke-width", chart.theme("lineBorderWidth"));
        Path p = (Path)el("path", attr);

        p.MoveTo(x.getDouble(0), y.getDouble(0));

        if (Brush.SYMBOL_CURVE.equals(options.getString("symbol"))) {
            JSONObject px = this.curvePoints(x);
            JSONObject py = this.curvePoints(y);

            for (int i = 0, len = x.length(); i < len; i++) {
                p.CurveTo(
                        px.getJSONArray("p1").getDouble(i),
                        py.getJSONArray("p1").getDouble(i),
                        px.getJSONArray("p2").getDouble(i),
                        py.getJSONArray("p2").getDouble(i),
                        x.getDouble(i + 1),
                        y.getDouble(i + 1)
               );
            }
        } else {
            for (int i = 0, len = x.length() -1; i < len; i++) {
                if(Brush.SYMBOL_STEP.equals(options.getString("symbol"))) {
                    p.LineTo(x.getDouble(i), y.getDouble(i + 1));
                }

                p.LineTo(x.getDouble(i + 1), y.getDouble(i + 1));
            }
        }
        return p;
    }
}
