package com.baizhi.wy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnEntity implements Serializable {
    private String title;
    private List<City> citys;
}
