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
@Table(name = "yx_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Feedback implements Serializable {
    @Id
    private String id;

    private String title;

    private String content;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "save_date")
    private Date saveDate;

}