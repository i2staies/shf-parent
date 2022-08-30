package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.HouseUser;
import com.itguigu.mapper.HouseUserMapper;
import com.itguigu.controller.HouseUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {


    @Autowired
    private HouseUserMapper houseUserMapper;
    @Override
    public BaseMapper<HouseUser> getBaseMapper() {
        return houseUserMapper;
    }

    @Override
    public List<HouseUser> findListByHouseId(Long id) {
        return houseUserMapper.findListByHouseId(id);
    }
}
