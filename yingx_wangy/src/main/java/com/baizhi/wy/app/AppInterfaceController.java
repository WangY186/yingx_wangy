package com.baizhi.wy.app;

import com.baizhi.wy.common.CommonResult;
import com.baizhi.wy.entity.User;
import com.baizhi.wy.entity.Video;
import com.baizhi.wy.po.CategoryPo;
import com.baizhi.wy.po.DetailVideoPo;
import com.baizhi.wy.po.LikeVideoPo;
import com.baizhi.wy.po.VideoPo;
import com.baizhi.wy.service.CategoryService;
import com.baizhi.wy.service.UserService;
import com.baizhi.wy.service.VideoService;
import com.baizhi.wy.util.SendPhoneCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app")
public class AppInterfaceController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

     @RequestMapping("getPhoneCode")
     public CommonResult getPhoneCode(String phone, HttpSession session){
         String radom = SendPhoneCodeUtil.getRadom(6);
         //先将验证码存入session
         session.setAttribute("code",radom);
         String s = SendPhoneCodeUtil.sendCode(phone, radom);
         if(s.equals("发送成功")){
             return new CommonResult().success("发送成功",phone);
         }else{
             return new CommonResult().failed("发送失败"+s,null);
         }
     }

     //首页显示所有视频
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
         try{
             List<VideoPo> videoPos = videoService.queryByReleaseTime();
             return new CommonResult().success(videoPos);
         }catch(Exception e){
             e.printStackTrace();
             return new CommonResult().failed(null);
         }
    }
    //首页搜索视频功能
    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){
        try{
            List<LikeVideoPo> likeVideoPos = videoService.queryByLikeVideoName(content);
            return new CommonResult().success(likeVideoPos);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult().failed(null);
        }
    }
    //点击查看视频详情
    @RequestMapping("queryByVideoDetail")
    public CommonResult queryByVideoDetail(String videoId,String userId){

        try{
            DetailVideoPo detailVideoPo = videoService.queryByVideoDetail(videoId,userId);
            return new CommonResult().success(detailVideoPo);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult().failed(null);
        }
    }
    //前台查询类别
    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory(){
        try{
            List<CategoryPo> categoryPos = categoryService.queryAllCategory();
            return new CommonResult().success(categoryPos);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult().failed(null);
        }
    }

    //前台根据类别查询视频
    @RequestMapping("queryCateVideoList")
    public CommonResult queryCateVideoList(String cateId){

        try{
            List<LikeVideoPo> likeVideoPos = videoService.queryCateVideoList(cateId);
            return new CommonResult().success(likeVideoPos);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult().failed(null);
        }
    }
    //用户登录注册
    @RequestMapping("login")
    public CommonResult login(String phone,String phoneCode,HttpSession session){
         session.setAttribute("code","123456");
        Map<String, Object> login=userService.login(phone, phoneCode);
            if(login.get("status").equals("100")){
                return new CommonResult().success("登录成功",login.get("message"));
            }else{
                return new CommonResult().failed(login.get("status").toString(),login.get("message").toString(),null);
            }
    }
    //根据用户ID查询用户发布的视频
    @RequestMapping("queryByUserVideo")
    public CommonResult queryByUserVideo(String userId){
        try{
            List<LikeVideoPo> likeVideoPos = videoService.queryByUserVideo(userId);
            return new CommonResult().success(likeVideoPos);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult().failed(null);
        }
    }
    //添加视频
    @RequestMapping("save")
    public CommonResult save(Video video){
         return null;
    }
    //查看该登录用户关注其他用户发布的视频

    @RequestMapping("queryByUserMoving")
    public CommonResult queryByUserMoving(String userId){
        try{
            List<DetailVideoPo> detailVideoPos = videoService.queryByUserMoving(userId);
            return new CommonResult().success(detailVideoPos);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult().failed(null);
        }
    }
}
