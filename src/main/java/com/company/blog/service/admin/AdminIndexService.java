package com.company.blog.service.admin;

import com.company.blog.dao.admin.AdminIndexDao;
import com.company.blog.model.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminIndexService {

    @Autowired
    private AdminIndexDao adminIndexDao;

    @Transactional
    public void addBlog(Blog blog) {
        adminIndexDao.addBlog(blog);
    }

    @Transactional
    public Blog findBlogById(Long id) {
        return adminIndexDao.findBlogById(id);
    }

    @Transactional
    public List<Blog> findAllBlog() {
        return adminIndexDao.findAllBlog();
    }

    @Transactional
    public void updateBlog(Blog blog) {
        adminIndexDao.updateBlog(blog);
    }

    @Transactional
    public void deleteBlog(Long id) {
        adminIndexDao.deleteBlog(id);
        adminIndexDao.file1();
        adminIndexDao.file2();
        adminIndexDao.file3();
    }

    @Transactional
    public List<Blog> Search(String title, Long type) {
        if (title.trim().isEmpty()) {
            if (type != null) {
                return adminIndexDao.SearchByType(type);
            } else {
                return adminIndexDao.findAllBlog();
            }
        } else if (type != null) {
            return adminIndexDao.SearchByTitleAndType(title, type);
        } else if (type == null){
            return adminIndexDao.SearchByTitle(title);
        }
        return null;
    }

    @Transactional
    public void deleteBlogByTypeId(Long id) {
        adminIndexDao.deleteBolgByTypeId(id);
        adminIndexDao.file1();
        adminIndexDao.file2();
        adminIndexDao.file3();
    }

}
