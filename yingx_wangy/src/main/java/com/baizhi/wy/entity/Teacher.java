package com.baizhi.wy.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements Serializable {
    @Excel(name = "ID",needMerge = true)
    private String id;

    @Excel(name="教师姓名",needMerge = true)
    private String name;
    @ExcelCollection(name="学生信息")
    private List<Emp> emps;
}
