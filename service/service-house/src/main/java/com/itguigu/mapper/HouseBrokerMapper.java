package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long id);
}
