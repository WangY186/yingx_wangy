package com.baizhi.wy.controller;

import com.baizhi.wy.entity.Category;
import com.baizhi.wy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page,Integer rows,Integer levels,String parentId){
        Map<String, Object> map = categoryService.showAll(page, rows, levels,parentId);
        return map;
    }

    @RequestMapping("option")
    public Object option(Category category,String oper){
        if("add".equals(oper)){
            categoryService.add(category);
        }
        if("edit".equals(oper)){
           categoryService.update(category);
        }
        if("del".equals(oper)){
            Map<String, Object> map = categoryService.delete(category);
            return map;
        }
        return null;
    }

    @RequestMapping("queryTwo")
    public List<Category> queryTwo(){
        List<Category> categories = categoryService.queryTwo();
        return  categories;
    }
    @RequestMapping("queryOne")
    public List<Category> queryOne(){
        List<Category> categories = categoryService.queryOne();
        return  categories;
    }

}
