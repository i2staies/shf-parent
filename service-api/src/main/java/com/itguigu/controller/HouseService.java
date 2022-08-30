package com.itguigu.controller;

import com.github.pagehelper.PageInfo;
import com.itguigu.base.BaseService;
import com.itguigu.entity.House;
import com.itguigu.entity.bo.HouseQueryBo;
import com.itguigu.entity.vo.HouseVo;

public interface HouseService extends BaseService<House> {

    void publish(Long id, Integer status);

    /**
     * 分页查询
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @param houseQueryBo 搜索条件，排序条件
     * @return
     */
    PageInfo<HouseVo> findByPage(Integer pageNum, Integer pageSize, HouseQueryBo houseQueryBo);
}
