package com.company.blog.service;

import com.company.blog.dao.InitialDao;
import com.company.blog.model.entity.*;
import com.company.blog.service.admin.AdminTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InitialService {

    @Autowired
    private InitialDao initialDao;

    @Autowired
    private AdminTagService adminTagService;

    @Transactional
    public List<Blog> findAllBlogBySearch(String query) {
       return initialDao.findAllBlogBySearch(query);
    }

    @Transactional
    public List<Type> findAllType(List<Type> Types) {
        for (Type type : Types) {
            type.setBlogs(initialDao.findAllBlogByTypeId(type.getId()));
        }
        return Types;
    }

    @Transactional
    public List<Tag> findAllTag(List<Tag> Tags) {
        for (Tag tag : Tags) {
            tag.setBlogs(initialDao.findAllBlogByTagName(tag.getName()));
        }
        return Tags;
    }

    @Transactional
    public List<Blog> findAllBlogByDate() {
        return initialDao.findAllBlogByDate();
    }

    @Transactional
    public List<Tag> getTagsByNames(String tagsName) {
        String[] str =  tagsName.split(",");
        List<Tag> tags = new ArrayList<Tag>();
        for (String s : str) {
            Tag tag = adminTagService.findTagByName(s);
            tags.add(tag);
        }
        return tags;
    }

    @Transactional
    public List<Comment> findAllCommentByTrunk(Long id) {
        return initialDao.findAllCommentByTrunk(id);
    }

    @Transactional
    public Map<Integer, List<Comment>> findSonCommentByTrunkId(List<Comment> comments) {
        Map<Integer, List<Comment>> map = new HashMap<Integer, List<Comment>>();
        int key = -1;
        for (Comment comment : comments) {
            if (comment.getSonId() != null) {
                List<Comment> list = initialDao.findSonCommentByTrunkId(comment.getId());
                map.put(comment.getId().intValue(), list);
            } else {
                map.put(key, null);
                key-=1;
            }
        }
        return map;
    }

    @Transactional
    public void addComment(Comment comment, String atname, User user, Long cid) {
        //  如没有点回复，则为主干评论
        if (atname.trim().isEmpty()) {
            comment.setParentId(null);
            initialDao.addComment(comment);
        } else if (user != null) {
            //  判断为管理员，有session的话不管表单输入的名字是否和用户名一样都为管理员提交
            StringBuffer stringBuffer = new StringBuffer().append("up: ")
                    .append(comment.getComtent());
            comment.setComtent(stringBuffer.toString());

            //  判断回复的评论有没有父评论(有的话直接添加，没有的话设置子评论)
            Comment c = initialDao.findCommentByParentId(cid);
            if (c.getSonId() == null) {
                initialDao.setCommentBySonId(cid);
            }
            //  添加评论
            comment.setParentId(cid);
            initialDao.addCommentByadmin(comment);
        } else {
            //  普通用户
            StringBuffer stringBuffer = new StringBuffer().append("@")
                    .append(atname + " ").append(comment.getComtent());
            comment.setComtent(stringBuffer.toString());

            Comment c = initialDao.findCommentByParentId(cid);
            if (c.getSonId() == null) {
                initialDao.setCommentBySonId(cid);
            }
            comment.setParentId(cid);
            initialDao.addComment(comment);
        }
    }

}
