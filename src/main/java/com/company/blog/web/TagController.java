package com.company.blog.web;

import com.company.blog.model.entity.Blog;
import com.company.blog.model.entity.Tag;
import com.company.blog.service.InitialService;
import com.company.blog.service.TagService;
import com.company.blog.service.admin.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    private AdminIndexService adminIndexService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private InitialService initialService;

    @Autowired
    private AdminTagService adminTagService;

    @Autowired
    private TagService tagService;


    //  进入分类主页面
    @GetMapping("/tag")
    public String AllBlogByTag(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = adminIndexService.findAllBlog();
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
            b.setTags(initialService.getTagsByNames(b.getTagsName()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("pages", lists);

        List<Tag> tags = initialService.findAllTag(adminTagService.findAllTag());
        model.addAttribute("tags", tags);

        return "tag";
    }

    //  由链接进入主页面
    @GetMapping("/tag/{name}")
    public String BlogByTag(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model, @PathVariable String name) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = tagService.findAllBlogByTagName(name);
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
            b.setTags(initialService.getTagsByNames(b.getTagsName()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("pages", lists);

        List<Tag> tags = initialService.findAllTag(adminTagService.findAllTag());
        model.addAttribute("tags", tags);
        return "tag";
    }
}
