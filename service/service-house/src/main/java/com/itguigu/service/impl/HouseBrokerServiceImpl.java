package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.HouseBroker;
import com.itguigu.mapper.HouseBrokerMapper;
import com.itguigu.controller.HouseBrokerService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerMapper houseBrokerMapper;
    @Override
    public BaseMapper<HouseBroker> getBaseMapper() {
        return houseBrokerMapper;
    }

    @Override
    public List<HouseBroker> findListByHouseId(Long id) {
        return houseBrokerMapper.findListByHouseId(id);
    }
}
