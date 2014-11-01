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
public class LineBrush extends Brush {
    private Transform root;
    private String symbol;

    public LineBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }
    public LineBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {

        root = el("g").translate(chart.x(), chart.y());
        symbol = options.optString("symbol", "normal");
    }



    @Override
    public Object draw() {
        return drawLine(getXY());
    }

    protected Object drawLine(OptionArray path) {
        for(int k = 0, len = path.length(); k < len; k++) {
            root.append(createLine((Option) path.object(k), k));
        }

        return opt().put("root", root);
    }

    protected Path createLine(Option pos, int index) {
        OptionArray x = (OptionArray) pos.array("x");
        OptionArray y = (OptionArray) pos.array("y");

        Path p = (Path)el("path", opt()
            .stroke(color(index))
            .strokeWidth(chart.theme("lineBorderWidth"))
        );

        p.MoveTo(x.D(0), y.D(0));

        if (Brush.SYMBOL_CURVE.equals(symbol)) {
            Option px = this.curvePoints(x);
            Option py = this.curvePoints(y);

            for (int i = 0, len = x.length(); i < len; i++) {
                p.CurveTo(
                        px.array("p1").getDouble(i),
                        py.array("p1").getDouble(i),
                        px.array("p2").getDouble(i),
                        py.array("p2").getDouble(i),
                        x.D(i + 1),
                        y.D(i + 1)
               );
            }
        } else {
            for (int i = 0, len = x.length() -1; i < len; i++) {
                if(Brush.SYMBOL_STEP.equals(symbol)) {
                    p.LineTo(x.D(i), y.D(i + 1));
                }

                p.LineTo(x.D(i + 1), y.D(i + 1));
            }
        }
        return p;
    }
}
