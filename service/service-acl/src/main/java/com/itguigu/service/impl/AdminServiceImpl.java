package com.itguigu.service.impl;


import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.Admin;
import com.itguigu.entity.AdminRole;
import com.itguigu.entity.Role;
import com.itguigu.mapper.AdminMapper;
import com.itguigu.controller.AdminService;
import com.itguigu.mapper.AdminRoleMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public BaseMapper<Admin> getBaseMapper() {
        return adminMapper;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        //删除用户原先角色
        adminRoleMapper.deleteByAdminId(adminId);
        //添加新角色
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleMapper.insert(adminRole);
        }
    }

    @Override
    public Admin getByUsername(String username) {
            return adminMapper.getByUsername(username);
    }

    @Override
    public String getRoleByAdminId(Long adminId) {
//        Role role = adminMapper.getRoleByAdminId(adminId);
        return "管理员";
    }
}