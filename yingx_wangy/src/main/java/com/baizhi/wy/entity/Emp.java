package com.baizhi.wy.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Emp implements Serializable {
    @Excel(name="ID")
    private String id;

    @Excel(name="姓名")
    private String name;

    @Excel(name="年龄")
    private Integer age;

    @Excel(name="生日",format = "yyyy年MM月dd日",width=20)
    private Date bir;
}
