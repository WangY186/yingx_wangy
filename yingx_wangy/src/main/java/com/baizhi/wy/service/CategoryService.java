package com.baizhi.wy.service;

import com.baizhi.wy.entity.Category;
import com.baizhi.wy.po.CategoryPo;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    //分页查询分类
    Map<String,Object> showAll(Integer page,Integer rows,Integer levels,String parentId);
    //添加分类
    void add(Category category);
    //删除分类
    Map<String,Object> delete(Category category);
    //修改分类
    void update(Category category);
    //查询所有的二级类别
    List<Category> queryTwo();
    //查询所有一级类别
    List<Category> queryOne();

    //前台类别查询
    List<CategoryPo> queryAllCategory();




}
