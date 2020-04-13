package com.baizhi.wy.dao;

import com.baizhi.wy.entity.Video;
import com.baizhi.wy.po.DetailVideoPo;
import com.baizhi.wy.po.LikeVideoPo;
import com.baizhi.wy.po.VideoPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {

   List<Video> queryAll(@Param("start")Integer start,@Param("end")Integer end);

   //前台App视频首页查询
   List<VideoPo> queryByReleaseTime();

   //前台根据视频名称模糊查询
   List<LikeVideoPo> queryByLikeVideoName(String content);

   //视频详情
   DetailVideoPo queryByVideoDetail(String videoId);

   List<LikeVideoPo> queryByCateId(@Param("cateId")String cateId,@Param("videoId")String videoId);

   //前台根据类别查询视频
   List<LikeVideoPo> queryCateVideoList(String cateId);
   //根据用户id查询视频
   List<LikeVideoPo> queryByUserVideo(String userId);
   //查询所有视频的详细信息
   List<DetailVideoPo> queryByUserMoving();
}