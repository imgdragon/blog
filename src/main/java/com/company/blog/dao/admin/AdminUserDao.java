package com.company.blog.dao.admin;

import com.company.blog.model.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserDao {

    @Select("select * from t_user where username=#{username} and password=#{password}")
    public User findByUser(String username, String password);

    @Select("select * from t_user where id=#{id}")
    public User findUserById(Long id);

}
