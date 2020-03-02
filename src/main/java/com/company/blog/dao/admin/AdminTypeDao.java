package com.company.blog.dao.admin;

import com.company.blog.model.entity.Type;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminTypeDao {

    @Insert("insert into t_type(name) values(#{name})")
    public void addType(Type type);

    @Select("select * from t_type where id=#{id}")
    public Type findTypeById(Long id);

    @Select("select * from t_type where name=#{name}")
    public Type findTypeByName(String name);

    @Select("select * from t_type")
    public List<Type> findAllType();

    @Update("update t_type set name=#{name} where id=#{id}")
    public void updateType(Long id, String name);

    @Delete("delete from t_type where id=#{id}")
    public void deleteType(Long id);

    @Update("alter table t_type drop id")
    public void file1();
    @Update("alter table t_type add id int(3) first")
    public void file2();
    @Update("alter table t_type modify column id int(3) not null auto_increment,add primary key(id)")
    public void file3();


}
