package com.baizhi.wy.controller;

import com.baizhi.wy.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> map = feedbackService.showAll(page, rows);
        return map;
    }
}
