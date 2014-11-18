jennifer-chart
==============

implements simple svg Jennifer chart for java

# Maven 
```xml
<dependencies>
    <dependency>
        <groupId>com.jennifersoft.chart</groupId>
        <artifactId>chart</artifactId>
        <version>0.4</version>
        <type>jar</type>
        <scope>provided</scope>			
    </dependency>
</dependencies>	
...

<repositories>
    <repository>
        <id>jennifer-chart-mvn-repo</id>
        <url>https://raw.github.com/easylogic/jennifer-chart/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>	

```

# Sample 
```java

Option chartOpt = new Option();
//chartOpt.put("theme", "dark");
chartOpt.put("width", "800");
chartOpt.put("height", "800");
chartOpt.put("grid", new Option());
chartOpt.put("widget", new OptionArray());
chartOpt.put("data", new OptionArray());
chartOpt.put("brush", new OptionArray());

// grid
Option grid = (Option) chartOpt.object("grid");
grid.put("x", new Option());
grid.put("y", new Option());

Option x = (Option) grid.object("x");
Option y = (Option) grid.object("y");

x.put("type", "range").put("target", "{value} + {value2}").put("step", 10).put("line", true);
y.put("type", "block").put("target", "name").put("line", true);
// brush

OptionArray brush = (OptionArray) chartOpt.array("brush");
brush.put(Brush.stackbar().put("target", new OptionArray().put("value").put("value2")));

OptionArray widget = (OptionArray) chartOpt.array("widget");
widget.put(Widget.legend().put("align", "start"));
widget.put(Widget.title().put("text", "sample title"));

// data

OptionArray data = (OptionArray) chartOpt.array("data");

long now = System.currentTimeMillis();

for(int i = 0; i < 10; i++) {
    Option d = new Option();

    d.put("name", "tab" + i);
    d.put("value", i * 2 + 0.1);
    d.put("value2", i * 3 + 5);
    d.put("time", TimeUtil.add(now, "seconds", i));

    data.put(d);
}		

ChartBuilder chart = new ChartBuilder(chartOpt);

System.out.println(chart.render());

```