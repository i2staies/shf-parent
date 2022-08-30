package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    /**
     * 根据用户手机号查询用户信息，如果手机号已存在，则不能注册
     * @param phone
     * @return
     */
    UserInfo getByPhone(String phone);
}
