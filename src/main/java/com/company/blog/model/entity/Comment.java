package com.company.blog.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment {

    private Long id;
    private String username;
    private String email;
    private String comtent;
    private String avatar;  //头像
    private LocalDateTime createTime;

    private Long blogId;    //  属于哪个博客
    private Long parentId;  //  父评论ID
    private Long sonId;     //  子评论ID

}
