package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据roleId查询角色权限信息
     * @param roleId
     * @return
     */
    List<Map<String,Object>> findZNodesByRoleId(Long roleId);

    /**
     * 根据adminId查询菜单信息
     * @param adminId
     * @return
     */
    List<Permission> findMenusByAdminId(Long adminId);

    /**
     * 查询所有菜单信息
     * @return
     */
    List<Permission> findAllMenus();

}
