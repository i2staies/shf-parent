package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    List<Long> findPermissionIdListByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void insert(RolePermission rolePermission);

}
