package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.controller.PermissionService;
import com.itguigu.entity.Permission;
import com.itguigu.helper.PermissionHelper;
import com.itguigu.mapper.PermissionMapper;
import com.itguigu.mapper.RolePermissionMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public BaseMapper<Permission> getBaseMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findZNodesByRoleId(Long roleId) {
        List<Map<String, Object>> zNodes = new ArrayList<>();
        //先查询所有权限
        List<Permission> allPermissionList = permissionMapper.findAll();
        //在查询已分配的权限的id
        List<Long> assingPermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);

        for (Permission permission : allPermissionList) {
            Map<String,Object> zNode = new HashMap<>();
            if (assingPermissionIdList.contains(permission.getId())) {
                //已分配权限
                zNode.put("checked", true);
            }else {
                zNode.put("checked", false);
            }
            zNode.put("id", permission.getId());
            zNode.put("pId", permission.getParentId());
            zNode.put("name", permission.getName());
            zNode.put("open", true);
            zNodes.add(zNode);
        }
        return zNodes;
    }

    @Override
    public List<Permission> findMenusByAdminId(Long adminId) {
        //返回的只是一个普通集合
        //build之后为具有子父关系的集合
        List<Permission> menus = permissionMapper.findMenusByAdminId(adminId);
        return PermissionHelper.build(menus);
    }

    @Override
    public List<Permission> findAllMenus() {
        List<Permission> list = permissionMapper.findAll();
        return PermissionHelper.build(list);
    }
}
