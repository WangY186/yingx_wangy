package com.baizhi.wy.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Excel(name="ID")
    private String id;

    @Excel(name="名字")
    private String name;

    @Excel(name = "头像",type = 2,width = 15,height = 15,savePath = "D:/img")
    private String img;
}
