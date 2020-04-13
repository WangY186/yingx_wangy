package com.baizhi.wy.dao;

import com.baizhi.wy.entity.City;
import com.baizhi.wy.entity.MonthCount;
import com.baizhi.wy.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<User> queryByPage(@Param("start")Integer start, @Param("end")Integer end);
    List<MonthCount> queryBySexAndMonth(String sex);

    List<City> queryBySexAndCity(String sex);

    //查询所有的月份
    List<String> queryAllMonth();

}