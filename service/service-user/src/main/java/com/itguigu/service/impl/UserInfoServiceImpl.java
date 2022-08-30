package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.UserInfo;
import com.itguigu.mapper.UserInfoMapper;
import com.itguigu.controller.UserInfoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public BaseMapper<UserInfo> getBaseMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo getByPhone(String phone) {
        return userInfoMapper.getByPhone(phone);
    }


}
