package com.itguigu.security;

import com.itguigu.entity.Admin;
import com.itguigu.entity.Permission;
import com.itguigu.controller.AdminService;
import com.itguigu.controller.PermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//UserDetailsService是security自带的接口
public class UserDetailServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;
    @Override
    //loadUserByUsername根据姓名加载用户信息
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据username查询用户信息
        Admin admin = adminService.getByUsername(username);
        if(admin == null){
            //登录失败
            //返回值是一个UserDetail，封装username和password，返回之后会和页面上输入的账户作比较
            return null;
        }
        //账户正确
        //权限列表（code）
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //动态授权
        List<Permission> permissionList = permissionService.findMenusByAdminId(admin.getId());
        for (Permission permission : permissionList) {
            if(permission.getCode() == null || "".equals(permission.getCode())){
                continue;
            }
            authorities.add(new SimpleGrantedAuthority(permission.getCode()));
        }
        return new User(admin.getUsername(),admin.getPassword(),authorities);
    }
}
