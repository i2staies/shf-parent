package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findListByTypeAndHouseId(Long id,Integer type);
}
