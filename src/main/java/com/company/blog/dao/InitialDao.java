package com.company.blog.dao;

import com.company.blog.model.entity.Blog;
import com.company.blog.model.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InitialDao {

    @Select("select * from t_blog where type_id=#{id}")
    List<Blog> findAllBlogByTypeId(Long id);

    @Select("select * from t_blog where tags_name like CONCAT('%',#{name},'%')")
    List<Blog> findAllBlogByTagName(String name);

    @Select("select * from t_blog order by update_time DESC")
    List<Blog> findAllBlogByDate();

    @Select("select * from t_blog where title like CONCAT('%',#{query},'%') or description like CONCAT('%',#{query},'%')")
    List<Blog> findAllBlogBySearch(String query);

    @Select("select * from t_comment where parent_id is null and blog_id=#{id} order by create_time DESC")
    List<Comment> findAllCommentByTrunk(Long id);

    @Select("select * from t_comment where parent_id=#{id} order by create_time DESC")
    List<Comment> findSonCommentByTrunkId(Long id);

    @Insert("insert into t_comment values(null,#{username}, #{email}, #{comtent}, #{avatar}, #{createTime}, #{blogId},#{parentId},#{sonId})")
    void addComment(Comment comment);

    @Insert("insert into t_comment values(null,#{username}, #{email}, #{comtent}, #{avatar}, #{createTime}, #{blogId},#{parentId},#{sonId})")
    void addCommentByadmin(Comment comment);

    @Select("select * from t_comment where id=#{parentId}")
    Comment findCommentByParentId(Long parentId);

    @Update("update t_comment set son_id=#{id} where id=#{id}")
    void setCommentBySonId(Long id);

}
