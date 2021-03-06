package com.company.blog.dao;

import com.company.blog.model.entity.Blog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TagDao {

    @Select("select * from t_blog where tags_name like CONCAT('%',#{name},'%')")
    List<Blog> findAllBlogByTagName(String name);

}
