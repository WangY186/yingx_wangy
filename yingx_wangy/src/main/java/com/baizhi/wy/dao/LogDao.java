package com.baizhi.wy.dao;

import com.baizhi.wy.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogDao {
    List<Log> queryAll(@Param("start")Integer start, @Param("end") Integer end);

    Integer count();

    void insert(Log log);

}
