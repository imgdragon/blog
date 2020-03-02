package com.company.blog.service.admin;

import com.company.blog.dao.admin.AdminUserDao;
import com.company.blog.model.entity.User;
import com.company.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
public class AdminUserService {
    @Autowired
    private AdminUserDao adminUserDao;

    @Transactional
    public User findByUser(String username, String password) {
        User user = adminUserDao.findByUser(username, MD5Utils.code(password));
        return user;
    }

    @Transactional
    public User findUserById(Long id) {
        return adminUserDao.findUserById(id);
    }

}
