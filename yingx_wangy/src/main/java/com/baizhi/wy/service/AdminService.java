package com.baizhi.wy.service;

import com.baizhi.wy.entity.Admin;

import java.util.Map;


public interface AdminService {
     Map<String,Object> login(Admin admin, String code);
}
