package com.company.blog.web;

import com.company.blog.model.entity.*;
import com.company.blog.service.InitialService;
import com.company.blog.service.admin.AdminIndexService;
import com.company.blog.service.admin.AdminTagService;
import com.company.blog.service.admin.AdminTypeService;
import com.company.blog.service.admin.AdminUserService;
import com.company.blog.util.MarkdownUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class InitialController {

    @Autowired
    private AdminIndexService adminIndexService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminTagService adminTagService;

    @Autowired
    private InitialService initialService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        //  返回主博客集
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = adminIndexService.findAllBlog();
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("pages", lists);
        //  返回分类数组并带有博客
        List<Type> types = (initialService.findAllType(adminTypeService.findAllType())).subList(0,6);
        model.addAttribute("types", types);
        //  返回标签数组并带有博客
        List<Tag> tags = (initialService.findAllTag(adminTagService.findAllTag())).subList(0,8);
        model.addAttribute("tags", tags);
        //  返回更新日期最新的博客
        List<Blog> blogs = initialService.findAllBlogByDate().subList(0,6);
        model.addAttribute("blogs", blogs);
        return "index";
    }

    //  搜索博客(按标题及简介搜索)
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         Model model,@RequestParam String query) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = initialService.findAllBlogBySearch(query);
        for (Blog b : allBlog) {
            b.setType(adminTypeService.findTypeById(b.getTypeId()));
            b.setUser(adminUserService.findUserById(b.getUserId()));
        }
        PageInfo<Blog> lists = new PageInfo<>(allBlog);
        model.addAttribute("pages", lists);
        return "search";
    }

    //  通过博客ID查找博客(额外通过该博客标签字符串切割，再通过名称查找对应的标签集合)+评论
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = adminIndexService.findBlogById(id);
        blog.setUser(adminUserService.findUserById(blog.getUserId()));
        blog.setTags(initialService.getTagsByNames(blog.getTagsName()));
        //  单一处理MarkDown文本形成html内容,新建一个博客对象作存储避免对数据库更改
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setComtent(MarkdownUtils.markdownToHtmlExtensions(b.getComtent()));
        model.addAttribute("blog", b);
        //  通过一条博客的id查找该博客的主干评论
        List<Comment> comments = initialService.findAllCommentByTrunk(id);
        model.addAttribute("comments", comments);
        //  通过主干的id去查找有没有子评论(集合)
        Map<Integer, List<Comment>> sonComment = initialService.findSonCommentByTrunkId(comments);
//        for (Map.Entry<Integer, List<Comment>> entry : sonComment.entrySet()) {
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());
//        }
        model.addAttribute("soncomment", sonComment);

        return "blog";
    }

    //  添加博客评论
    @PostMapping("/blog/submit")
    public String submitByBlogFrom(@RequestParam Long blogId, @RequestParam Long parentid,
                                   @RequestParam String atname, @RequestParam Long cid, Comment comment, HttpSession session) {
        comment.setBlogId(blogId);
        comment.setCreateTime(LocalDateTime.now());
        comment.setParentId(parentid);
        comment.setAvatar("https://unsplash.it/100/100?image=1005");

        User user = (User) session.getAttribute("user");
        initialService.addComment(comment, atname, user, cid);
        return "redirect:/blog/" + blogId;
    }


}
