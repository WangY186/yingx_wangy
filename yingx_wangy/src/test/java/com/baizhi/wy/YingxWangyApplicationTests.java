package com.baizhi.wy;

import com.baizhi.wy.dao.CategoryMapper;
import com.baizhi.wy.dao.UserMapper;
import com.baizhi.wy.dao.VideoMapper;
import com.baizhi.wy.entity.User;
import com.baizhi.wy.entity.UserExample;
import com.baizhi.wy.entity.Video;
import com.baizhi.wy.entity.VideoExample;
import com.baizhi.wy.po.CategoryPo;
import com.baizhi.wy.po.DetailVideoPo;
import com.baizhi.wy.po.LikeVideoPo;
import com.baizhi.wy.resposity.VideoResposity;
import com.baizhi.wy.service.CategoryService;
import com.baizhi.wy.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxWangyApplicationTests {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    VideoResposity videoResposity;
    
    @Autowired
    VideoService videoService;

   @Test
    public void test7(){
       DetailVideoPo detailVideoPo = videoMapper.queryByVideoDetail("00ee12132e674736b476f4cb7d40d7ee");

       List<LikeVideoPo> likeVideoPos = videoMapper.queryByCateId(detailVideoPo.getCategoryId(),"00ee12132e674736b476f4cb7d40d7ee");
       detailVideoPo.setVideoList(likeVideoPos);
       System.out.println(detailVideoPo);

   }

    @Test
    public void test8(){

        List<CategoryPo> categoryPos = categoryService.queryAllCategory();
        for (CategoryPo categoryPo : categoryPos) {
            System.out.println(categoryPo);
        }
    }
    @Test
    public void test(){
        VideoExample example = new VideoExample();
        List<Video> videos = videoMapper.selectByExample(example);
        for (Video video : videos) {
            videoResposity.save(video);
        }
    }

    @Test
    public void test1(){
        videoService.querySearch("抖音")
    }
    
}
