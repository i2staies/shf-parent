package com.itguigu.mapper;


import com.itguigu.base.BaseMapper;
import com.itguigu.entity.Admin;
import com.itguigu.entity.Role;

public interface AdminMapper extends BaseMapper<Admin> {

    Admin getByUsername(String username);

    Role getRoleByAdminId(Long adminId);
}
