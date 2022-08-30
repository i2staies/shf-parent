package com.itguigu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.House;
import com.itguigu.entity.bo.HouseQueryBo;
import com.itguigu.entity.vo.HouseVo;
import com.itguigu.mapper.HouseMapper;
import com.itguigu.controller.HouseService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;
    @Override
    public BaseMapper<House> getBaseMapper() {
        return houseMapper;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        houseMapper.update(house);
    }

    @Override
    public PageInfo<HouseVo> findByPage(Integer pageNum, Integer pageSize, HouseQueryBo houseQueryBo) {
        PageHelper.startPage(pageNum,pageSize);
        Page<HouseVo> page = houseMapper.findByPage(houseQueryBo);
        return new PageInfo(page);
    }
}
