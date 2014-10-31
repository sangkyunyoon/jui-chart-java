package com.jennifer.ui.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jayden on 2014-10-31.
 */
public class OptionArray extends JSONArray {

    public JSONObject object(int i) { return optJSONObject(i); }
    public JSONArray array(int i) { return optJSONArray(i); }
    public double D(int i) { return getDouble(i); }
    public int I(int i) { return getInt(i); }
    public long L(int i) { return getLong(i); }
    public String string(int i) { return getString(i); }

}
