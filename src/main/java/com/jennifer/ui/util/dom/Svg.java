package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.DomUtil;
import com.jennifer.ui.util.Option;

public class Svg extends DomUtil{

    public Svg(Option attr) {
        super("svg", attr);
    }

    public Svg() {
        super("svg");
    }

    @Override
    public void init() {
        put("xmlns", "http://www.w3.org/2000/svg");
    }

    public String toXml() {
        // xml 리턴
        String str = "<?xml version='1.1' encoding='utf-8' ?>\r\n<!DOCTYPE svg>\r\n";
        return str + toString();
    }

    public void save(String path) {
        // 파일 저장
    }

}
