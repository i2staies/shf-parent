package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> findListByHouseId(Long id);
}
