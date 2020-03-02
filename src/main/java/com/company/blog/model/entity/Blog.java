package com.company.blog.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Blog implements Serializable {

    private Long id;
    private String photo;    //首图
    private String title;   //标题
    private String comtent;     //内容
    private String flag;    //标记(原创，转载...)
    private Boolean opengood;   //是否开启赞赏功能
    private Boolean openstatement;     //是否开启个人声明
    private Boolean opencomment;    //是否开启评论功能
    private Boolean recommend;  //是否设置为推荐文章
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;   //创建博客的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;   //修改博客的时间
    private String description;     //博客基本描述

    private Long userId;    //设置用户索引
    private long typeId;    //设置分类索引
    private String tagsName;  //设置多个标签索引

    private Type type;
    private List<Tag> tags;
    private User user;

    private List<Comment> comments;

}
