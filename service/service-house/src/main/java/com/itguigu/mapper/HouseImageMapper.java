package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageMapper extends BaseMapper<HouseImage> {
    List<HouseImage> findListByTypeAndHouseId( @Param("houseId") Long id, @Param("type") Integer type);
}
