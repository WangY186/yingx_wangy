package com.baizhi.wy.po;

import com.baizhi.wy.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPo {
    private String id;
    private String cateName;
    private Integer levels;
    private String parentId;
    private List<Category> categoryList;

}
