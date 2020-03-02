package com.company.blog.dao.admin;

import com.company.blog.model.entity.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminTagDao {

    @Insert("insert into t_tag(name) values(#{name})")
    public void addTag(Tag tag);

    @Select("select * from t_tag where id=#{id}")
    public Tag findTagById(Long id);

    @Select("select * from t_tag where name=#{name}")
    public Tag findTagByName(String name);

    @Select("select * from t_tag")
    public List<Tag> findAllTag();

    @Update("update t_tag set name=#{name} where id=#{id}")
    public void updateTag(Long id, String name);

    @Delete("delete from t_tag where id=#{id}")
    public void deleteTag(Long id);

    @Update("alter table t_type drop id")
    public void file1();
    @Update("alter table t_type add id int(3) first")
    public void file2();
    @Update("alter table t_type modify column id int(3) not null auto_increment,add primary key(id)")
    public void file3();

}
