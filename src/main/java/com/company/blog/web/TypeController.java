package com.company.blog.web;


import com.company.blog.model.entity.Blog;
import com.company.blog.model.entity.Type;
import com.company.blog.service.InitialService;
import com.company.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private AdminIndexService adminIndexService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private InitialService initialService;

    @Autowired
    private TypeService typeService;

    //  进入分类主页面
    @GetMapping("/type")
    public String AllBlogByType(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = adminIndexService.findAllBlog();
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("pages", lists);

        List<Type> types = (initialService.findAllType(adminTypeService.findAllType()));
        model.addAttribute("types", types);

        return "type";
    }

    //  由链接进入主页面
    @GetMapping("/type/{id}")
    public String BlogByType(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model, @PathVariable Long id) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = typeService.findAllBlogByTypeId(id);
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("pages", lists);

        List<Type> types = (initialService.findAllType(adminTypeService.findAllType()));
        model.addAttribute("types", types);
        return "type";
    }

}
