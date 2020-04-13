package com.baizhi.wy.service;

import com.baizhi.wy.entity.Log;

import java.util.Map;

public interface LogService {
    Map<String,Object> showAll(Integer page, Integer rows);


    void add(Log log);
}
