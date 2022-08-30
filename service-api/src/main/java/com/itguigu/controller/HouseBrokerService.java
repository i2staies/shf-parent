package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long id);
}
