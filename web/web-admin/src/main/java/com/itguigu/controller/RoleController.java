package com.itguigu.controller;

import com.github.pagehelper.PageInfo;
import com.itguigu.base.BaseController;
import com.itguigu.entity.Role;
import org.apache.dubbo.config.annotation.Reference;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static final String PAGE_ASSIGN_SHOW = "role/assignShow";
    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    private static final String PAGE_INDEX = "role/index";

    private static final String PageCreate = "role/create";

    private static final String LIST_ACTION = "redirect:/role";


    private final static String PAGE_EDIT = "role/edit";

    @RequestMapping
    @PreAuthorize("hasAnyAuthority('role.show')")
    public String index(@RequestParam Map filters, Model model) {
        //数据回显
        model.addAttribute("filters", filters);
        //根据filters分页查询
//        List<Role> list = roleService.findAll();
        PageInfo<Role> pageinfo = roleService.findPage(filters);

        model.addAttribute("page", pageinfo);

        return PAGE_INDEX;
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('role.create')")
    public String create() {
        return PageCreate;
    }

    @PostMapping("/save")
    public String save(Role role, Model model) {
        roleService.insert(role);

        return successPage(model, "添加角色成功");
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasAnyAuthority('role.edit')")
    public String edit(Model model, @PathVariable Long id) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return PAGE_EDIT;
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('role.update')")
    public String update(Role role, Model model) {
        roleService.update(role);
        return successPage(model, "更新角色成功");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('role.delete')")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 跳转‘role/assignShow.html页面
     *
     * @param id 角色id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @GetMapping("/assignShow/{id}")
    public String assignShow(@PathVariable Long id, Model model) throws IOException {
        model.addAttribute("roleId", id);
        List<Map<String, Object>> zNodes = permissionService.findZNodesByRoleId(id);
        /*返回的应为json字符串
         * json.parse：将json字符串变为了json对象
         * */
        model.addAttribute("zNodes", new ObjectMapper().writeValueAsString(zNodes));
        return PAGE_ASSIGN_SHOW;
    }

    @PostMapping("/assignPermission")
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    public String assignPermission(Long roleId, Long[] permissionIds,Model model) {
        roleService.assignPermission(roleId,permissionIds);
        return successPage(model,"保存权限成功");
    }
}
