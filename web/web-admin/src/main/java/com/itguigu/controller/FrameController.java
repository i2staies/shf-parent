package com.itguigu.controller;

import com.itguigu.entity.Admin;
import com.itguigu.entity.Permission;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FrameController {

    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";
    private static final String PAGE_LOGIN = "frame/login";
    private static final String PAGE_AUTH = "frame/auth";

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;


    /**
     * 框架首页
     * 跳转页面需要permissionList和admin(headUrl和name)
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {

        //从security容器中获取登录的用户信息
        //Authentication:认证对象信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Admin admin = adminService.getByUsername(username);
        Long adminId = admin.getId();

        model.addAttribute("admin", admin);
        //permissionList
        //通过adminId
        List<Permission> permissionList = permissionService.findMenusByAdminId(adminId);
        model.addAttribute("permissionList",permissionList);

        //TODO: 连表acl_admin,acl_role 查询角色名称
        String roleName = adminService.getRoleByAdminId(adminId);
        model.addAttribute("roleName", roleName);
        return PAGE_INDEX;
    }

    /**
     * 框架主页
     *
     * @return
     */
    @GetMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return PAGE_LOGIN;
    }

    @RequestMapping("/auth")
    public String auth(){
        return PAGE_AUTH;
    }

}

