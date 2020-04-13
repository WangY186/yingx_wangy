package com.baizhi.wy.util;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;

public class QueryGoEasyUtil {

    public static void queryGoEasy(Object o){
        String s = JSON.toJSONString(o);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-81f2dddc6c054171b2d7168db3c647d5");
        goEasy.publish("user_channel", s);
    }
}
