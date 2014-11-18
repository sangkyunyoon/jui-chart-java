jennifer-chart
==============

implements Jennifer simple SVG chart for java

reference to https://github.com/seogi1004/jui


# Maven 
```xml
<dependencies>
    <dependency>
        <groupId>com.jennifersoft.chart</groupId>
        <artifactId>chart</artifactId>
        <version>0.1</version>
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

# Features
* pure java svg library
* implements JUI chart's most brushes
* use JSONObject as option 
* 5 Grids, 25 Brushes, 2 Widgets 


# Sample 
```java

// Option is equals JSONObject  
// OptionArray is equals JSONArray  


// Setting Chart Options 
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

// create chart builder 
ChartBuilder chart = new ChartBuilder(chartOpt);

// print svg code 
System.out.println(chart.render());

```


## License

MIT License 

Copyright (C) 2014 (```JenniferSoft Inc.```)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
