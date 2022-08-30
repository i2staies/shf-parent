package com.itguigu.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itguigu.util.CastUtil;

import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl<T> {

    public abstract BaseMapper<T> getBaseMapper();

    public void insert(T role) {
        getBaseMapper().insert(role);
    }

    public void delete(Long id) {
        getBaseMapper().delete(id);
    }


    public void update(T role) {
        getBaseMapper().update(role);
    }


    public T getById(Long id) {
        return getBaseMapper().getById(id);
    }

    public PageInfo<T> findPage(Map filters) {
        Integer pageNum = CastUtil.castInt(filters.get("pageNum"),1);
        Integer pageSize = CastUtil.castInt(filters.get("pageSize"),5);
        PageHelper.startPage(pageNum, pageSize);
        List<T> roleList = getBaseMapper().findPage(filters);
        return new PageInfo<>(roleList);
    }

    public List<T> findAll() {
        return getBaseMapper().findAll();
    }

}
