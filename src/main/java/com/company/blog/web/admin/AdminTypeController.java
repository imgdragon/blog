package com.company.blog.web.admin;

import com.company.blog.model.entity.Type;
import com.company.blog.service.admin.AdminIndexService;
import com.company.blog.service.admin.AdminTypeService;
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
public class AdminTypeController {

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private AdminIndexService adminIndexService;

    //  得到所有的分类列表
    @GetMapping("/types")
    public String typeList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model){
        PageHelper.startPage(pageNum, 8);
        List<Type> allType = adminTypeService.findAllType();
        //得到分页结果对象
        PageInfo<Type> lists = new PageInfo<>(allType);
        model.addAttribute("pages", lists);

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input() {
        return "admin/types-input";
    }

    //  新增分类列表
    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){
        Type t = adminTypeService.findTypeByName(type.getName());
        if(t == null) {
            attributes.addFlashAttribute("message", "添加分类成功！");
            adminTypeService.addType(type);
            return "redirect:/admin/types";
        } else {
            attributes.addFlashAttribute("message", "已存在该分类，请重置！");
            return "redirect:/admin/types/input";
        }
    }

    //  修改分类列表时回显
    @GetMapping("/types/{id}/input")
    public String echoType(@PathVariable Long id, Model model) {
        model.addAttribute("type", adminTypeService.findTypeById(id));
        return "admin/types-update";
    }

    //  修改分类列表
    @PostMapping("/types/update")
    public String editType(Type type, RedirectAttributes attributes){
        adminTypeService.updateType(type.getId(), type.getName());
        attributes.addFlashAttribute("message", "修改分类成功！");
        return "redirect:/admin/types";
    }

    //  删除分类列表
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
        adminIndexService.deleteBlogByTypeId(id);

        adminTypeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除分类成功！");
        return "redirect:/admin/types";
    }

}
