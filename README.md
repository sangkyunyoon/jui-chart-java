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
* implements JUI chart's most features (https://github.com/seogi1004/jui)
* use JSONObject as option 
* 5 Grids, 25 Brushes, 2 Widgets 


# Sample 
```java

// Option is equals JSONObject  
// OptionArray is equals JSONArray  

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.Option;
import com.jennifer.ui.util.OptionArray;
import com.jennifer.ui.util.TimeUtil;

public class ChartUtil {
	
	public ChartUtil() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void main(String args[]) throws Exception {
		
        ChartBuilder chart = new ChartBuilder(800, 800);

        chart.grid("x", new Option().type("range").target("{value} + {value2}").step(10).line(true));
        chart.grid("y", new Option().type("block").line(true).put("target","name"));
        chart.widget(new Option().type("title").text("sample title"));
        chart.widget(new Option().type("legend").align("start"));
        chart.brush(new Option().type("stackbar").target(new OptionArray().put("value").put("value2")));

        long now = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            Option d = new Option();

            d.put("name", "tab" + i);
            d.put("value", i * 2 + 0.1);
            d.put("value2", i * 3 + 5);
            d.put("time", TimeUtil.add(now, "seconds", i));

            chart.add(d);
        }

        System.out.println(chart.render());
	}
}

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
