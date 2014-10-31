package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class PathBrush extends Brush {


    private Transform root;
    private int count;
    private Grid c;

    public PathBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public PathBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());
        count = chart.data().length();

        c = (Grid)options.get("c");
    }

    @Override
    public Object draw() {

        JSONArray target = options.array("target");

        for(int ti = 0, len = target.length(); ti < len; ti++) {
            String color = color(ti);

            Path path = root.path(opt()
                    .fill(color)
                    .fillOpacity(chart.theme("pathOpacity"))
                    .stroke(color)
                    .strokeWidth(chart.theme("pathBorderWidth"))
            );


            for (int i = 0; i < count; i++) {
                double value = chart.dataDouble(i, target.getString(ti));
                Option obj = c.get(i, value);

                if (i == 0) {
                    path.MoveTo(obj.x(), obj.y());
                } else {
                    path.LineTo(obj.x(), obj.y());
                }
            }

            path.Close();
        }

        return opt().put("root", root);
    }
}
