package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.HouseUser;

import java.util.List;

public interface HouseUserMapper extends BaseMapper<HouseUser> {
    List<HouseUser> findListByHouseId(Long id);
}
