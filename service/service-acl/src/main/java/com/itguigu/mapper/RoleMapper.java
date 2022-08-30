package com.itguigu.mapper;


import com.itguigu.base.BaseMapper;
import com.itguigu.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role>{

    /*查询用户*/
    List<Role> findAll();

}
