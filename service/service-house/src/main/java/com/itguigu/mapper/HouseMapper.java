package com.itguigu.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.itguigu.base.BaseMapper;
import com.itguigu.entity.House;
import com.itguigu.entity.bo.HouseQueryBo;
import com.itguigu.entity.vo.HouseVo;

public interface HouseMapper extends BaseMapper<House> {
    Page<HouseVo> findByPage(HouseQueryBo houseQueryBo);
}
