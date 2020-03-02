package com.company.blog.dao.admin;

import com.company.blog.model.entity.Blog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminIndexDao {

    @Insert("insert into t_blog(photo,title,comtent,flag,opengood,openstatement,opencomment,recommend,create_time,update_time,user_id,type_id,tags_name,description)" +
            " values(#{photo}, #{title}, #{comtent}, #{flag},#{opengood},#{openstatement},#{opencomment},#{recommend}," +
            " #{createTime}, #{updateTime}, #{userId}, #{typeId}, #{tagsName}, #{description})")
    public void addBlog(Blog blog);


    @Select("select * from t_blog where id=#{id}")
    public Blog findBlogById(Long id);


    @Select("select * from t_blog")
    @Results(id = "Map", value = {
            @Result(column = "id", property = "id"),

            @Result(property = "type", column = "id",
                    one = @One(select = "com.company.blog.dao.admin.AdminTypeDao.findTypeById", fetchType = FetchType.LAZY)),
            @Result(property = "user", column = "id",
                    one = @One(select = "com.company.blog.dao.admin.AdminUserDao.findUserById", fetchType = FetchType.LAZY))
    })
    List<Blog> findAllBlog();

    @Update("update t_blog set photo=#{photo},title=#{title},comtent=#{comtent},flag=#{flag},opengood=#{opengood},openstatement=#{openstatement},opencomment=#{opencomment}," +
            "recommend=#{recommend},update_time=#{updateTime},type_id=#{typeId},tags_name=#{tagsName},description=#{description} where id=#{id}")
    public void updateBlog(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    public void deleteBlog(Long id);

    @Delete("delete from t_blog where type_id=#{id}")
    public void deleteBolgByTypeId(Long id);

    @Select("select * from t_blog where title like CONCAT('%',#{title},'%') and type_id=#{id}")
    public List<Blog> SearchByTitleAndType(String title, Long id);

    @Select("select * from t_blog where title like CONCAT('%',#{title},'%')")
    public List<Blog> SearchByTitle(String title);

    @Select("select * from t_blog where type_id=#{id}")
    public List<Blog> SearchByType(Long id);

    @Update("alter table t_blog drop id")
    public void file1();
    @Update("alter table t_blog add id int(3) first")
    public void file2();
    @Update("alter table t_blog modify column id int(3) not null auto_increment,add primary key(id)")
    public void file3();

}
