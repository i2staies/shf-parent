package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.AdminRole;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    /**
     * 根据用户id查询其分配的角色id列表
     * @param adminId
     * @return
     */
    List<Long> findRoleIdListByAdminId(Long adminId);

    /**
     * 根据admin去删除已分配的角色
     * @param adminId
     */
    void deleteByAdminId(Long adminId);
}
