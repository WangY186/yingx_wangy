package com.baizhi.wy;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.wy.entity.Emp;
import com.baizhi.wy.entity.Photo;
import com.baizhi.wy.entity.Teacher;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyPoi {

    @Test
    public void createBucket() {
        List<Emp> list = new ArrayList<>();
        list.add(new Emp("1", "王五", 18, new Date()));
        list.add(new Emp("2", "赵丽颖", 40, new Date()));
        list.add(new Emp("3", "张三", 20, new Date()));
        list.add(new Emp("4", "李四", 22, new Date()));
        list.add(new Emp("5", "赵六", 30, new Date()));
        Teacher teacher1 = new Teacher("1", "Suns", list);
        Teacher teacher2 = new Teacher("2", "Huxinz", list);
        List<Teacher> list2= new ArrayList<>();
        list2.add(teacher1);
        list2.add(teacher2);
        ExportParams exportParams = new ExportParams("教师信息表","student");

        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Teacher.class, list2);
        try {
            sheets.write(new FileOutputStream(new File("D:/学生表.xls")));
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2(){
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        params.setTitleRows(1);

        try {
            FileInputStream inputStream = new FileInputStream(new File("D:/学生表.xls"));
            List<Teacher> teachers = ExcelImportUtil.importExcel(inputStream, Teacher.class, params);
            for (Teacher teacher : teachers) {
                System.out.println(teacher);
                List<Emp> emps = teacher.getEmps();
                for (Emp emp : emps) {
                    System.out.println(emp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void createBucket2() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo("1", "王五","src/main/webapp/img/3.png" ));
        list.add(new Photo("2", "赵丽颖","src/main/webapp/img/7.png" ));
        list.add(new Photo("3", "张三", "src/main/webapp/img/9.png"));
        list.add(new Photo("4", "李四","src/main/webapp/img/11.png" ));
        list.add(new Photo("5", "赵六", "src/main/webapp/img/13.png"));

        ExportParams exportParams = new ExportParams("用户头像","userimage");

        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Photo.class, list);
        try {
            sheets.write(new FileOutputStream(new File("D:/头像表.xls")));
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createBucket3() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);

        try {
            FileInputStream inputStream = new FileInputStream(new File("D:/头像表.xls"));
            List<Photo> objects = ExcelImportUtil.importExcel(inputStream, Photo.class, params);
            for (Photo object : objects) {
                System.out.println(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
