package com.baizhi.wy.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVideoPo {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private Date uploadTime;
    private String description;
    private String cateName;
    private String categoryId;
    private Integer likeCount;
    private String userId;
    private String userPicImg;
    private String userName;
    private Integer playCount;
    private Boolean isAttention;
    private List<LikeVideoPo> videoList;
}
