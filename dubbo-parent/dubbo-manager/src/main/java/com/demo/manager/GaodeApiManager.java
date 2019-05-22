package com.demo.manager;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface GaodeApiManager {
    public JSONObject geoEncodeService(Map<String,String> param);
}
