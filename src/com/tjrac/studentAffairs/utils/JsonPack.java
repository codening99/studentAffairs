package com.tjrac.studentAffairs.utils;

/**
 * JsonPack 将Map转化为Json
 *
 * @author : xziying
 * @create : 2020-09-22 08:16
 */
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JsonPack {
    public static String pack(Map<String, Object> map){
        JSONObject json = new JSONObject();
        for (String s : map.keySet()) {
            Object obj = map.get(s);
            json.put(s, obj);
        }
        return json.toString();
    }
}
