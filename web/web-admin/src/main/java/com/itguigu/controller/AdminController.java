package com.itguigu.controller;

import com.github.pagehelper.PageInfo;
import com.itguigu.base.BaseController;
import com.itguigu.entity.Admin;
import com.itguigu.entity.Role;
import com.itguigu.util.FileUtil;
import com.itguigu.util.QiniuUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
    private static final String PAGE_UPLOAD_SHOW = "admin/upload";
    @Reference
    private AdminService adminService;


    private final static String PAGE_INDEX = "admin/index";

    private static final String PAGE_CREATE = "admin/create";

    private final static String PAGE_EDIT = "admin/edit";

    private final static String LIST_ACTION = "redirect:/admin";

    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }


    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Admin admin, Model model){
        adminService.insert(admin);
        return successPage(model,"添加用户成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Admin admin,Model model){
        adminService.update(admin);

        return successPage(model,"编辑用户成功");
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        adminService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable Long id,Model model){
        model.addAttribute("id", id);
        return PAGE_UPLOAD_SHOW;
    }

    @PostMapping("upload/{id}")
    public String upload(@PathVariable Long id,Model model, @RequestParam("file")MultipartFile file) throws IOException {
        //设置随机名称，调用了getUUIDName，自动将前缀随机改变
        String originalFilename = file.getOriginalFilename();
        String uuidName = FileUtil.getUUIDName(originalFilename);
        //上传图片
        QiniuUtils.upload2Qiniu(file.getBytes(),uuidName);

        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl("http://rh5fot5uw.hn-bkt.clouddn.com/"+uuidName);

        adminService.update(admin);
        return successPage(model,"上传头像成功");
    }

    @Reference
    private RoleService roleService;
    private final static String PAGE_ASSIGN_SHOW = "admin/assignShow";

    @GetMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable("adminId") Long adminId,Model model){
        model.addAttribute("adminId", adminId);
        Map<String, List<Role>> roleMap = roleService.findRolesByAdminId(adminId);
        model.addAllAttributes(roleMap);
        return PAGE_ASSIGN_SHOW;
    }

    @PostMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds,Model model){
        adminService.assignRole(adminId,roleIds);
        return successPage(model,"分配角色成功！");
    }

}
