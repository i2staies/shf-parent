package com.itguigu.service.impl;


import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.Role;
import com.itguigu.entity.RolePermission;
import com.itguigu.mapper.AdminRoleMapper;
import com.itguigu.mapper.RoleMapper;

import com.itguigu.controller.RoleService;
import com.itguigu.mapper.RolePermissionMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public BaseMapper<Role> getBaseMapper() {
        return roleMapper;
    }

    @Override
    public Map<String, List<Role>> findRolesByAdminId(Long adminId) {
        //1. 查询所有角色
        List<Role> allRoleList = roleMapper.findAll();
        //2. 查询用户已分配的角色id列表
        List<Long> assignRoleIdList = adminRoleMapper.findRoleIdListByAdminId(adminId);
        //3. 创建俩List分别存储用户已分配和未分配的角色
        List<Role> unAssignRoleList = new ArrayList<>();
        List<Role> assignRoleList = new ArrayList<>();
        //4. 筛选用户已分配和未分配角色
        for (Role role : allRoleList) {
            if (assignRoleIdList.contains(role.getId())) {
                //已分配
                assignRoleList.add(role);
            }else {
                //未分配
                unAssignRoleList.add(role);
            }
        }

        Map<String,List<Role>> roleMap = new HashMap<>();
        roleMap.put("unAssignRoleList",unAssignRoleList);
        roleMap.put("assignRoleList",assignRoleList);
        return roleMap;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        //删除之前的权限
        rolePermissionMapper.deleteByRoleId(roleId);
        //添加新的权限
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.insert(rolePermission);
        }
    }
}
