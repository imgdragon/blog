package com.company.blog.dao;

import com.company.blog.model.entity.Blog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TypeDao {

    @Select("select * from t_blog where type_id=#{id}")
    List<Blog> findAllBlogByTypeId(Long id);
}
