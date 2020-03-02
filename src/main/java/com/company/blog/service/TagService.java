package com.company.blog.service;

import com.company.blog.dao.TagDao;
import com.company.blog.dao.admin.AdminTagDao;
import com.company.blog.model.entity.Blog;
import com.company.blog.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagDao tagDao;


    @Transactional
    public List<Blog> findAllBlogByTagName(String name) {
        return tagDao.findAllBlogByTagName(name);
    }

}
