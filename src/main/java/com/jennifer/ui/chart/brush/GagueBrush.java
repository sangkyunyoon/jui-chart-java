package com.jennifer.ui.chart.brush;

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.chart.grid.Grid;
import com.jennifer.ui.util.MathUtil;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.dom.Path;
import com.jennifer.ui.util.dom.Transform;
import org.json.JSONObject;

import static com.jennifer.ui.util.DomUtil.el;
import static com.jennifer.ui.util.Option.opt;

/**
 * Created by Jayden on 2014-10-27.
 */
public class GagueBrush extends DonutBrush {

    private Transform root;
    private double min;
    private double max;
    private double value;
    private double rate;
    private double size;
    private double startAngle;
    private double endAngle;
    private boolean arrow;
    private String unitText;
    private double w;
    private double centerX;
    private double centerY;
    private double outerRadius;
    private double innerRadius;

    public GagueBrush(ChartBuilder chart, Option options) {
        super(chart, options);
    }

    public GagueBrush(ChartBuilder chart, JSONObject options) {
        super(chart, options);
    }

    @Override
    public void drawBefore() {
        root = el("g").translate(chart.x(), chart.y());

        int width = chart.width(), height = chart.height();
        int min = width;

        if (height < min) {
            min = height;
        }

        w = min / 2.0;
        centerX = width / 2.0;
        centerY = height / 2.0;
        outerRadius = w;


        // default value load
        this.min =  options.optDouble("min", 0);
        this.max =  options.optDouble("max", 100);
        this.value =  options.optDouble("value", 0);
        this.rate =  options.optDouble("rate", 100);
        this.size =  options.optDouble("size", 60);
        this.startAngle =  options.optDouble("startAngle", 0);
        this.endAngle =  options.optDouble("endAngle", 360);
        this.arrow =  options.optBoolean("arrow", true);
        this.unitText =  options.optString("unitText", "");

        innerRadius = outerRadius - size;

    }

    @Override
    public Object draw() {

        double rate = (value - min) / (max - min);
        double currentAngle = (endAngle) * rate;

        if (endAngle >= 360) {
            endAngle = 359.99999;
        }

        Transform g = this.drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle + currentAngle, endAngle - currentAngle, opt().fill(chart.theme("gaugeBackgroundColor")));

        root.append(g);

        g = this.drawDonut(centerX, centerY, innerRadius, outerRadius, startAngle, currentAngle, opt().fill(color(0)));

        root.append(g);

        if (arrow) {
            g = createArrow(startAngle, currentAngle);
            root.append(g);
        }

        g = createText(startAngle, endAngle, min, max, value);
        root.append(g);

        return opt().put("root", root);
    }

    private Transform createText(double startAngle, double endAngle, double min, double max, double value) {
        Transform g = el("g").translate(centerX, centerY);

        g.text(opt()
            .x(0)
            .y((arrow) ? 70 : 10)
            .textAnchor("middel")
            .fontFamily(chart.theme("fontFamily"))
            .fontSize("3em")
            .fontWeight(1000)
            .fill(color(0))
        ).textNode(value+"");

        if (!"".equals(unitText)) {
            g.text(opt()
                .x(0)
                .y(100)
                .textAnchor("middel")
                .fontFamily(chart.theme("fontFamily"))
                .fontSize("1.5em")
                .fontWeight(500)
                .fill(chart.theme("gaugeFontColor"))
            ).textNode(unitText);
        }

        // 바깥 지름 부터 그림
        double startX = 0;
        double startY = -outerRadius;

        // min
        Option obj = MathUtil.rotate(startX, startY, MathUtil.radian(startAngle));

        startX = obj.x();
        startY = obj.y();

        g.text(opt()
            .x(obj.x() + 30)
            .y(obj.y() + 20)
            .textAnchor("middel")
            .fontFamily(chart.theme("fontFamily"))
            .fill(chart.theme("gaugeFontColor"))
        ).textNode(min+"");


        // max
        // outer arc 에 대한 지점 설정
        obj = MathUtil.rotate(startX, startY, MathUtil.radian(endAngle));

        g.text(opt()
            .x(obj.x() + 30)
            .y(obj.y() + 20)
            .textAnchor("middel")
            .fontFamily(chart.theme("fontFamily"))
            .fill(chart.theme("gaugeFontColor"))
        ).textNode(max+"");

        return g;
    }

    private Transform createArrow(double startAngle, double currentAngle) {
        Transform g = el("g").translate(centerX, centerY);

        double startX = 0;
        double startY = -(outerRadius + 5);

        Path path = g.path(opt()
            .stroke(chart.theme("gaugeArrowColor"))
            .strokeWidth(0.2)
            .fill(chart.theme("gaugeArrowColor"))
        );

        path.MoveTo(startX, startY);
        path.LineTo(5, 0);
        path.LineTo(-5, 0);
        path.Close();

        // start angle
        path.rotate(startAngle);
        path.rotate(endAngle + startAngle);

        g.circle(opt().cx(0).cy(0).r(5).fill(chart.theme("gaugeArrowColor")));
        g.circle(opt().cx(0).cy(0).r(2).fill(chart.theme("gaugeArrowColor")));

        return g;
    }
}
