package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

    /**
     * 查询用户的角色列表
     * @param adminId
     * @return
     */
    Map<String,List<Role>> findRolesByAdminId(Long adminId);

    /**
     * 给指定角色分配权限
     * @param roleId 指定角色的id
     * @param permissionIds 权限
     */
    void assignPermission(Long roleId, Long[] permissionIds);
}
