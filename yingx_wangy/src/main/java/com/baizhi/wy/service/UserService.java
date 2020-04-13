package com.baizhi.wy.service;

import com.baizhi.wy.entity.ReturnEntity;
import com.baizhi.wy.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页查询所有值
    Map<String, Object> showAll(Integer page, Integer pageSize);

    //查询所有用户
    List<User> queryAll();

    //添加数据
    String addUser(User user);

    //上传文件
    void upload(MultipartFile headImg, String id);

    //更新状态
    String update(User user);

    //删除数据
    void delete(String[] id);

    //登录注册验证
    Map<String, Object> login(String phone, String phoneCode);

    //查询用户详细信息
    User queryByUserDetail(String userId);
    //根据性别和月份查询数据
    Map<String,Object> queryBySexAndMonth();

    //根据性别和城市查询数据
    List<ReturnEntity> queryBySexAndCity();
}