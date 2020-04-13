package com.baizhi.wy.controller;

import com.baizhi.wy.entity.Admin;
import com.baizhi.wy.service.AdminService;
import com.baizhi.wy.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(Admin admin, String code){
        Map<String, Object> map = adminService.login(admin, code);
        return map;
    }
    @RequestMapping("/getImageCode")
    @ResponseBody
    public void getImageCode(HttpSession session, HttpServletResponse response){
        String code = ImageCodeUtil.getSecurityCode();
        session.setAttribute("code",code);
        BufferedImage image = ImageCodeUtil.createImage(code);
        ServletOutputStream outputStream = null;
        //将生成的验证码图片
        try {
            outputStream=response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}
