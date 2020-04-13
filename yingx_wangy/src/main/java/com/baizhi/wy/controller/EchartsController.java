package com.baizhi.wy.controller;

import com.baizhi.wy.entity.City;
import com.baizhi.wy.entity.ReturnEntity;
import com.baizhi.wy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("echarts")
public class EchartsController {

    @Autowired
    private UserService userService;

    @RequestMapping("barEcharts")
    public Map<String,Object> barEcharts(){
        Map<String, Object> map = userService.queryBySexAndMonth();
        return map;
    }

    @RequestMapping("mapEcharts")
    public List<ReturnEntity> mapEcharts(){
        List<ReturnEntity> list = userService.queryBySexAndCity();
        return list;
    }
}
