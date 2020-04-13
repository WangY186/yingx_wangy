package com.baizhi.wy.serviceImpl;

import com.baizhi.wy.annotation.AddCaches;
import com.baizhi.wy.annotation.DelCache;
import com.baizhi.wy.annotation.LogAnnotation;
import com.baizhi.wy.dao.UserMapper;
import com.baizhi.wy.entity.*;
import com.baizhi.wy.service.UserService;
import com.baizhi.wy.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    HttpSession session;

    @AddCaches
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> showAll(Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        Integer start =(page-1)*pageSize;
        UserExample example = new UserExample();
        Integer count = userMapper.selectCountByExample(example);
        Integer total = count%pageSize==0? count/pageSize : count/pageSize+1;
        List<User> users = userMapper.queryByPage(start, pageSize);
        map.put("page",page);
        map.put("rows",users);
        map.put("total",total);
        map.put("records",count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryAll() {
        UserExample example = new UserExample();
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

   @DelCache
    @Override
    @LogAnnotation(name = "添加用户")
    public String addUser(User user) {
        String uuid = UUIDUtil.getUUID();
        user.setId(uuid);
        user.setCreateDate(new Date());
        user.setStatus("0");
        userMapper.insertSelective(user);
        return uuid;
    }


    @Override
    public void upload(MultipartFile headImg, String id) {
        //获取文件名字
        String originalFilename = headImg.getOriginalFilename();
        String name = new Date().getTime()+"-"+originalFilename;
        /*//上传到阿里云
        AliyunOssUtils.uploadAliyun(headImg,name);
        //重新设置数据库中文件路径
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        User user = new User();
        user.setHeadImg("https://yingx-wang.oss-cn-beijing.aliyuncs.com/"+name);
        userMapper.updateByExampleSelective(user,example);*/
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            headImg.transferTo(new File(realPath+"/"+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        User user = new User();
        user.setHeadImg("/img/"+name);
        userMapper.updateByExampleSelective(user,example);
    }

    @DelCache
    @Override
    @LogAnnotation(name = "修改用户")
    public String update(User user) {
        System.out.println(user);
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user,example);
        return user.getId();
    }

    @DelCache
    @Override
    @LogAnnotation(name = "删除用户")
    public void delete(String[] id) {
        List<String> list = new ArrayList<>();
        for (String s : id) {
            list.add(s);
        }
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(list);
        userMapper.deleteByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> login(String phone, String phoneCode) {
        Map<String,Object> map = new HashMap<>();
        //获取存入session的验证码
        String code = (String) session.getAttribute("code");
        //验证码正确
        if(phoneCode.equals(code)){
            //根据手机号码获取用户
            UserExample example = new UserExample();
            example.createCriteria().andPhoneEqualTo(phone);
            User user = userMapper.selectOneByExample(example);
            //判断用户存在
            if(user != null){
               session.setAttribute("user",user);
               map.put("status","100");
               map.put("message",user);
            }else{//用户不存在，插入用户
                String uuid = UUIDUtil.getUUID();
                User user1 = new User();
                user1.setId(uuid);
                user1.setPhone(phone);
                userMapper.insertSelective(user1);
                session.setAttribute("user",user1);
                map.put("status","100");
                map.put("message",user1);
            }

        }else{//验证码错误
            map.put("status","101");
            map.put("message","验证码错误");
        }

        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryByUserDetail(String userId) {

        User user = userMapper.selectByPrimaryKey(userId);
        return user;

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryBySexAndMonth() {
        Map<String,Object> map = new HashMap<>();
        //按升序获取月份集合
        List<String> months = userMapper.queryAllMonth();
        //获取性别为男的集合
        List<MonthCount> boys = userMapper.queryBySexAndMonth("男");
        //获取性别为女的集合
        List<MonthCount> girls = userMapper.queryBySexAndMonth("女");
        //定义集合按月份存放男孩的注册数量
        List<Integer> boysCount=new ArrayList<>();

        for(int i=0;i<months.size();i++){
            boolean flag =true;
            for(int j=0;j<boys.size();j++){
                if(boys.get(j).getMonth().equals(months.get(i))){
                    boysCount.add(boys.get(j).getCount());
                    flag=false;
                }
            }
            if(flag){
                boysCount.add(0);
            }
        }
        //定义集合按月份存放男孩的注册数量
        List<Integer> girlsCount=new ArrayList<>();
        for(int i=0;i<months.size();i++){
            boolean flag1 =true;
            for(int j=0;j<girls.size();j++){
                if(girls.get(j).getMonth().equals(months.get(i))){
                    girlsCount.add(girls.get(j).getCount());
                    flag1=false;
                }
            }
            if(flag1){
                girlsCount.add(0);
            }
        }
        map.put("month",months);
        map.put("boys",boysCount);
        map.put("girls",girlsCount);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ReturnEntity> queryBySexAndCity() {
        List<ReturnEntity> list = new ArrayList<>();
        //查询男孩的城市以及数量
        List<City> boyCities = userMapper.queryBySexAndCity("男");
        ReturnEntity boys=new ReturnEntity("小男孩",boyCities);
        //查询女孩的城市以及数量
        List<City> girlCities = userMapper.queryBySexAndCity("女");
        ReturnEntity girls=new ReturnEntity("小姑娘",girlCities);

        list.add(boys);
        list.add(girls);
        return list;
    }
}
