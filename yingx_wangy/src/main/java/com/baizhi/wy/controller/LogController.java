package com.baizhi.wy.controller;

import com.baizhi.wy.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogService logService;

    @RequestMapping("showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page,Integer rows){

        Map<String, Object> map = logService.showAll(page, rows);
        return map;
    }
}
