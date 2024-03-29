/*
 * Copyright (C) 2014 (JenniferSoft Inc.)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jennifer.ui.util.dom;

import com.jennifer.ui.util.DomUtil;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Svg extends DomUtil{

    public Svg(JSONObject attr) {
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

    public String toDataURL() {
        try {
            return "data:image/svg+xml;base64," + new String(Base64.encodeBase64(toXml().getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void save(String path) {
        // 파일 저장
    }

}
