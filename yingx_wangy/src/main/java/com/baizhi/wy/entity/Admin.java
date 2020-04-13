package com.baizhi.wy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "yx_admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Admin implements Serializable {
    @Id
    private String id;
    private String username;
    private String password;
}
