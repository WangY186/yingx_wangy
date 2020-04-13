package com.baizhi.wy.service;

import com.baizhi.wy.entity.Video;
import com.baizhi.wy.po.DetailVideoPo;
import com.baizhi.wy.po.LikeVideoPo;
import com.baizhi.wy.po.VideoPo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {

    Map<String,Object> showAll(Integer page, Integer rows);
    String addVideo(Video video);
    void upload(MultipartFile path,String id);
    void uploadAliyun(MultipartFile path,String id);
    void update(Video video);
    Map<String,Object> delete(Video video);
    //前台App首页查询视频
    List<VideoPo> queryByReleaseTime();

    //前台根据视频名称模糊查询
    List<LikeVideoPo> queryByLikeVideoName(String content);

    //视频详情
    DetailVideoPo queryByVideoDetail(String videoId,String userId);

    //前台根据类别查询视频
    List<LikeVideoPo> queryCateVideoList(String cateId);

    //根据用户id查询视频
    List<LikeVideoPo> queryByUserVideo(String userId);

    //查询登录用户关注的用户的视频动态
    List<DetailVideoPo> queryByUserMoving(String userId);

    //分页检索结果
    List<Video> querySearch(String content);

}
