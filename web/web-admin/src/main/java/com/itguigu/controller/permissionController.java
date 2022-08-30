package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.Permission;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class permissionController extends BaseController {

    private static final String PAGE_INDEX = "permission/index";
    @Reference
    private PermissionService permissionService;
    @RequestMapping
    public String index(Model model){
        List<Permission> permissionList = permissionService.findAllMenus();
        model.addAttribute("list",permissionList);
        return PAGE_INDEX;
    }

    private static final String PAGE_CREATE = "permission/create";
    @GetMapping("/create")
    public String create(Permission permission,Model model){
        model.addAttribute("permission",permission);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Permission permission,Model model){
        permissionService.insert(permission);
        return successPage(model,"添加菜单成功");
    }

    private static final String LIST_ACTION = "redirect:/permission";
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model){
        permissionService.delete(id);
        return LIST_ACTION;
    }

    private static final String PAGE_EDIT = "permission/edit";
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return PAGE_EDIT;
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,Permission permission,Model model){
        permission.setId(id);
        permissionService.update(permission);
        return successPage(model,"修改菜单成功");
    }

}
