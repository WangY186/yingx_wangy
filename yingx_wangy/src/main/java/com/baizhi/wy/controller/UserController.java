package com.baizhi.wy.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.baizhi.wy.common.CommonResult;
import com.baizhi.wy.entity.User;
import com.baizhi.wy.service.UserService;
import com.baizhi.wy.util.QueryGoEasyUtil;
import com.baizhi.wy.util.SendPhoneCodeUtil;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page, Integer rows){
        Map<String, Object> map = userService.showAll(page, rows);
        return map;
    }

    @RequestMapping("option")
    public String option(User user, String[] id, String oper){
        String uid = null;
       if("add".equals(oper)){
           uid = userService.addUser(user);
           Map<String, Object> map = userService.queryBySexAndMonth();
           QueryGoEasyUtil.queryGoEasy(map);
       }
       if("edit".equals(oper)){
          uid = userService.update(user);
          Map<String, Object> map = userService.queryBySexAndMonth();
          QueryGoEasyUtil.queryGoEasy(map);
       }
       if("del".equals(oper)){
         userService.delete(id);
         Map<String, Object> map = userService.queryBySexAndMonth();
         QueryGoEasyUtil.queryGoEasy(map);
       }
       return uid;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile headImg,String id){
        userService.upload(headImg,id);
    }

    @RequestMapping("send")
    public String send(String phoneNumber){
        //获取随机字符串
        String radom = SendPhoneCodeUtil.getRadom(6);
        //存入字符串
        System.out.println(radom);
        String s = SendPhoneCodeUtil.sendCode(phoneNumber, radom);
        return s;
    }
    //展示用户详细信息
    @RequestMapping("getUserDetails")
    public CommonResult queryByUserDetail(String userid){

        try{
            User user = userService.queryByUserDetail(userid);
            return new CommonResult().success(user);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult().failed(null);
        }
    }
    //导出用户信息
    @RequestMapping("export")
    public String export(){
        String message=null;
        //查询所有用户
        List<User> users = userService.queryAll();
        for (User user : users) {
            user.setHeadImg("src/main/webapp"+user.getHeadImg());
        }
        try {
            //设置导出表的标题，表格名称
            ExportParams exportParams = new ExportParams("用户信息表", "用户信息");
            //设置导出的用户信息
            Workbook sheets = ExcelExportUtil.exportExcel(exportParams, User.class, users);
            //导出
            sheets.write(new FileOutputStream(new File("D://用户信息.xls")));
            message="导出成功";
            sheets.close();
        } catch (Exception e) {
            e.printStackTrace();
            message="导出失败";
        }
        return message;
    }
}
