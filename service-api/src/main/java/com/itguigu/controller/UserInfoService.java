package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);
}