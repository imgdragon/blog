package com.company.blog.service;

import com.company.blog.dao.TypeDao;
import com.company.blog.model.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeService {

    @Autowired
    private TypeDao typeDao;

    @Transactional
    public List<Blog> findAllBlogByTypeId(Long id) {
        return typeDao.findAllBlogByTypeId(id);
    }
}
