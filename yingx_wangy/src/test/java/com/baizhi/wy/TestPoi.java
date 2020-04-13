package com.baizhi.wy;

import com.baizhi.wy.entity.Emp;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPoi {

    @Test
    public void createBucket(){
        List<Emp> list = new ArrayList<>();
        list.add(new Emp("1","王五",18,new Date()));
        list.add(new Emp("2","赵丽颖",40,new Date()));
        list.add(new Emp("3","张三",20,new Date()));
        list.add(new Emp("4","李四",22,new Date()));
        list.add(new Emp("5","赵六",30,new Date()));
        Workbook sheets = new HSSFWorkbook();
        //设置日期格式风格
        CellStyle cellStyle = sheets.createCellStyle();
        DataFormat dataFormat = sheets.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));
        //设置标题格式
        CellStyle cellStyle1 = sheets.createCellStyle();
        Font font = sheets.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short)15);
        font.setItalic(true);
        font.setUnderline(FontFormatting.U_SINGLE);
        cellStyle1.setFont(font);
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);

        Sheet sheet = sheets.createSheet("学生信息");
        sheet.setColumnWidth(3,20*256);
        //合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0,0,0,3);
        sheet.addMergedRegion(cellAddresses);
        //行标题
        Row row2 = sheet.createRow(0);
        row2.setHeight((short)(20*20));
        Cell cell1 = row2.createCell(0);
        cell1.setCellStyle(cellStyle1);
        cell1.setCellValue("学生信息表");
        //设置列标题风格
        Font font1 = sheets.createFont();
        font1.setFontName("宋体");
        font1.setBold(true);
        CellStyle cellStyle2 = sheets.createCellStyle();
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setFont(font1);
        //列标题
        Row row = sheet.createRow(1);
        row.setHeight((short)(15*20));
        String[] indexs={"ID","姓名","年龄","生日"};
        for (int i = 0; i < indexs.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle2);
            cell.setCellValue(indexs[i]);
        }
        for (int i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow(i+2);
            row1.createCell(0).setCellValue(list.get(i).getId());
            row1.createCell(1).setCellValue(list.get(i).getName());
            row1.createCell(2).setCellValue(list.get(i).getAge());
            Cell cell=row1.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(list.get(i).getBir());
        }
        try {
            sheets.write(new FileOutputStream(new File("D://学生信息表.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExport(){
        try {
            Workbook sheets = new HSSFWorkbook(new FileInputStream(new File("D://学生信息表.xls")));
            Sheet sheet = sheets.getSheet("学生信息");
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String id = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                double ages = row.getCell(2).getNumericCellValue();
                int age = (int)ages;
                Date bir = row.getCell(3).getDateCellValue();
                Emp emp = new Emp(id, name, age, bir);
                System.out.println(emp);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
