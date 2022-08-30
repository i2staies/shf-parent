package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    /**
     * 给用户分配角色
     * @param adminId
     * @param roleIds
     */
    void assignRole(Long adminId, Long[] roleIds);

    //根据username查询用户信息
    Admin getByUsername(String username);

    String getRoleByAdminId(Long adminId);
}
