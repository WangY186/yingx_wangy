package com.baizhi.wy;

import com.baizhi.wy.dao.UserMapper;
import com.baizhi.wy.entity.ReturnEntity;
import com.baizhi.wy.entity.User;
import com.baizhi.wy.entity.UserExample;
import com.baizhi.wy.service.UserService;
import org.apache.commons.collections4.SplitMapUtils;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        List<User> users = userMapper.selectAll();
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public  void test1(){
        User user = new User();
        user.setId("2");
        user.setStatus("正常");
        user.setWechat("13653524568");
        user.setUsername("嘻嘻");
        user.setSign("嘻嘻是一个大坏蛋");
        user.setPhone("13653524568");
        user.setCreateDate(new Date());
        user.setHeadImg("3.jpg");
        userMapper.insertSelective(user);
    }

    @Test
    public void test2(){
        UserExample example = new UserExample();
        RowBounds rowBounds = new RowBounds(0,2);
        List<User> users = userMapper.selectByExampleAndRowBounds(example, rowBounds);
        users.forEach(user -> System.out.println(user));
    }
    @Test
    public void test3(){
        List<User> users = userService.queryAll();
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void test4(){
        Map<String, Object> map = userService.showAll(1, 3);
        System.out.println(map);
    }

    @Test
    public void test5(){
        Map<String, Object> map = userService.queryBySexAndMonth();
        System.out.println(map);
    }


    @Test
    public void test6(){
        List<ReturnEntity> list = userService.queryBySexAndCity();
        System.out.println(list);
    }
}
