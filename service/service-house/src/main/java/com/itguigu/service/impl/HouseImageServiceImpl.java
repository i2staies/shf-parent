package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.HouseImage;
import com.itguigu.mapper.HouseImageMapper;
import com.itguigu.controller.HouseImageService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageMapper houseImageMapper;
    @Override
    public BaseMapper<HouseImage> getBaseMapper() {
        return houseImageMapper;
    }

    @Override
    public List<HouseImage> findListByTypeAndHouseId(Long id, Integer type) {
        return houseImageMapper.findListByTypeAndHouseId(id,type);
    }
}
