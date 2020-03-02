package com.company.blog.web;

import com.company.blog.model.entity.Blog;
import com.company.blog.service.admin.AdminIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArchivesController {

    @Autowired
    private AdminIndexService adminIndexService;

    @GetMapping("/archives")
    public String Archives(Model model) {
        List<Blog> blogs = adminIndexService.findAllBlog();
        model.addAttribute("blogs", blogs);
        return "archives";
    }

}
