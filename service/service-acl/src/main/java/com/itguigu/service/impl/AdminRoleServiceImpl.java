package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.AdminRole;
import com.itguigu.mapper.AdminRoleMapper;
import com.itguigu.controller.AdminRoleService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole> implements AdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public BaseMapper<AdminRole> getBaseMapper() {
        return adminRoleMapper;
    }
}
