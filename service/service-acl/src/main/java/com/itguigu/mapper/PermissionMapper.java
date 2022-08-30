package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findMenusByAdminId(Long adminId);

    List<Permission> findAll();

}
