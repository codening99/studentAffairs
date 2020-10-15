package com.tjrac.studentAffairs.utils;

/**
 * JsonPack 将Map转化为Json
 *
 * @author : xziying
 * @create : 2020-09-22 08:16
 */
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonPack {
    Map<String, Object> map;
    public JsonPack(){
        map = new LinkedHashMap<>();
    }
    public void put(String name, Object obj){
        map.put(name, obj);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String toJson(){
        return JsonPack.pack(map);
    }
    public static String pack(Map<String, Object> map){
        JSONObject json = new JSONObject();
        for (String s : map.keySet()) {
            Object obj = map.get(s);
            json.put(s, obj);
        }
        return json.toString();
    }
}
