package com.baizhi.wy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthCount implements Serializable {
    private String month;
    private Integer count;
}
