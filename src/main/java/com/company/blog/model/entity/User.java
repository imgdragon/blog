package com.company.blog.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;

    @Email
    private String email;
    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creaTetime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updaTetime;

    private List<Blog> blogs;
}
