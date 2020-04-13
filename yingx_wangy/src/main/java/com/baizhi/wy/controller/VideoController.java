package com.baizhi.wy.controller;

import com.baizhi.wy.entity.Video;
import com.baizhi.wy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> map = videoService.showAll(page, rows);
        return map;
    }

    @RequestMapping("option")
    public Object option(Video video,String oper){

        if("add".equals(oper)){
            String uid=videoService.addVideo(video);
            return uid;
        }
        if("edit".equals(oper)){
           videoService.update(video);
        }
        if("del".equals(oper)){
            Map<String, Object> map = videoService.delete(video);
            return map;
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile path,String id){
       videoService.uploadAliyun(path,id);
    }

    @RequestMapping("querySearch")
    public List<Video> querySearch(String content){
        List<Video> videos = videoService.querySearch(content);
        return videos;
    }
}
