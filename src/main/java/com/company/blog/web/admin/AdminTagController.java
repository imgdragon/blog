package com.company.blog.web.admin;

import com.company.blog.model.entity.Tag;
import com.company.blog.service.admin.AdminTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminTagController {

    @Autowired
    private AdminTagService adminTagService;

    //  得到所有的标签列表
    @GetMapping("/tags")
    public String tagList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model){
        PageHelper.startPage(pageNum, 8);
        List<Tag> allTag = adminTagService.findAllTag();
        //得到分页结果对象
        PageInfo<Tag> lists = new PageInfo<>(allTag);
        model.addAttribute("pages", lists);

        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input() {
        return "admin/tags-input";
    }

    //  新增标签列表
    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes){
        Tag t = adminTagService.findTagByName(tag.getName());
        if(t == null) {
            attributes.addFlashAttribute("message", "添加标签成功！");
            adminTagService.addTag(tag);
            return "redirect:/admin/tags";
        } else {
            attributes.addFlashAttribute("message", "已存在该标签，请重置！");
            return "redirect:/admin/tags/input";
        }
    }

    //  修改标签列表时回显
    @GetMapping("/tags/{id}/input")
    public String echoTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag", adminTagService.findTagById(id));
        return "admin/tags-update";
    }

    //  修改标签列表
    @PostMapping("/tags/update")
    public String editTag(Tag tag, RedirectAttributes attributes){
        adminTagService.updateTag(tag.getId(), tag.getName());
        attributes.addFlashAttribute("message", "修改标签成功！");
        return "redirect:/admin/tags";
    }

    //  删除标签列表
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        adminTagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除标签成功！");
        return "redirect:/admin/tags";
    }

}
