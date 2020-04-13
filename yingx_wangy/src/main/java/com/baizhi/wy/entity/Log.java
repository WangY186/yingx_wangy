package com.baizhi.wy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Log implements Serializable {
    @Id
    private String id;
    @Column(name = "admin_oper")
    private String adminOper;
    private Date time;
    private String operation;
    private String status;
}
