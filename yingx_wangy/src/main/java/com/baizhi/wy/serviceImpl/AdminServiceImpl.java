package com.baizhi.wy.serviceImpl;

import com.baizhi.wy.dao.AdminDao;
import com.baizhi.wy.entity.Admin;
import com.baizhi.wy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    HttpSession session;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> login(Admin admin, String code) {
        Map<String,Object> map = new HashMap<>();
        String code1 =(String)session.getAttribute("code");
        if(code1.equals(code)){
            Admin admin1 = adminDao.queryByUsername(admin.getUsername());
            if(admin1 == null){
                map.put("status","400");
                map.put("message","用户不存在");
            }else{
                if(admin1.getPassword().equals(admin.getPassword())){
                    session.setAttribute("admin",admin1);
                    map.put("status","200");
                    map.put("message","登陆成功");
                }else{
                    map.put("status","400");
                    map.put("message","密码错误");
                }
            }
        }else{
           map.put("status","400");
           map.put("message","验证码错误");
        }
        return map;
    }
}
