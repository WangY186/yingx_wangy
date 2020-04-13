package com.baizhi.wy.dao;

import com.baizhi.wy.entity.Admin;
import com.baizhi.wy.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<User> {
    Admin queryByUsername(String username);
}
