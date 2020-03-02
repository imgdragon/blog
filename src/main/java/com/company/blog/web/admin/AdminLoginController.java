package com.company.blog.web.admin;

import com.company.blog.model.entity.Blog;
import com.company.blog.model.entity.User;
import com.company.blog.service.admin.AdminIndexService;
import com.company.blog.service.admin.AdminTypeService;
import com.company.blog.service.admin.AdminUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminIndexService adminIndexService;

    @Autowired
    private AdminTypeService adminTypeService;

    @GetMapping
    public String loginPage () {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes,
                        @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        User user = adminUserService.findByUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);

            PageHelper.startPage(pageNum, 8);
            List<Blog> allBlog = adminIndexService.findAllBlog();
            for (Blog b : allBlog) {
                b.setType(adminTypeService.findTypeById(b.getTypeId()));
            }
            PageInfo<Blog> lists = new PageInfo<>(allBlog);
            model.addAttribute("types", adminTypeService.findAllType());
            model.addAttribute("pages", lists);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
