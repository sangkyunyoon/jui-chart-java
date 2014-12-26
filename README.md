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

// JSONObject is equals JSONObject
// JSONArray is equals JSONArray

import com.jennifer.ui.chart.ChartBuilder;
import com.jennifer.ui.util.TimeUtil;

public class ChartUtil {
	
	public ChartUtil() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void main(String args[]) throws Exception {
		
        String json = "{" +
        				"width: 756," +
        				"height : 220," +
        				"padding : { top: 40 }," +
        				"data : [] ," +
        		        "grid : {" +
        		        "   x : { " +
        		        "       type : 'block'," +
        		        "       target : 'start'," +
        		        "       line : true," +
        		        "		innerPadding : 10 " +
        		        "    }," +
        		        "   y : { " +
        		        "       type : 'range'," +
        		        "       target : ''," +
        		        "       unit : 100 " +
        		        "   }" +
        		        "}," +
        		        "brush : {" +
        		        "   type : 'column'," +
        		        "   target : ''," +
        		        "	colors : ['#19345b', '#5b3299', '#3f7cf4', '#47b2ac', '#badbac', '#3f5ca8', '#a9d8f8', '#ffc000', '#555d69', '#64b044']" +
        		        "}," +
        		        "widget : { type : 'title', text : '' }" +
        		        "}";			
        		        
        		JSONArray list = new JSONArray();
        		
        		// create data list 
        		//list.put(...);
        		
        		ChartBuilder chart = new ChartBuilder(json);
        		chart.set("data", list);
        		chart.set("grid.y.target", "count");
        		chart.set("brush.target", "count");
        		chart.set("widget.text", o.getString("title"));
        		
        		final long range = etime - stime; 
        		chart.set("grid.x.format", new ChartDateFormat() {
        		    public String format(long l) {
        		        if(range <= TemplateTimeUtil.ONE_DAY) {
        		            return TemplateTimeUtil.format(l, "HH:mm");
        		        } else if(range <= TemplateTimeUtil.ONE_DAY * 3) {
        		            return TemplateTimeUtil.format(l, "MM-dd HH");
        		        } else {
        		            return TemplateTimeUtil.format(l, "MM-dd");
        		        }
        		    }
        		});
        
        		
        		chart.set("grid.y.unit", new ChartUnit() {
        			public double getUnit(double max, double size) {
        				return ClientUtilities.getSplitUnit(max, (int) size);
        			}
        		});
        		
        		
        		//TODO : 파일 변환이 필요함 
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
