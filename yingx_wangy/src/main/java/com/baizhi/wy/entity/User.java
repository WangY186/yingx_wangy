package com.baizhi.wy.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Id
    @Excel(name="ID",width=40)
    private String id;

    @Excel(name="姓名")
    private String username;

    @Excel(name="手机号",width=15)
    private String phone;

    @Column(name="head_img")
    @Excel(name="头像",type=2,width = 20,height = 20)
    private String headImg;

    @Excel(name="简介",width=20)
    private String sign;

    @Excel(name="微信号",width=15)
    private String wechat;

    @Excel(name="状态",replace = {"正常_0","冻结_1"})
    private String status;

    @Column(name="create_date")
    @Excel(name="注册时间",format = "yyyy年MM月dd日",width = 20)
    private Date createDate;

    @Excel(name ="性别")
    private String sex;
    @Excel(name ="城市")
    private String area;

}