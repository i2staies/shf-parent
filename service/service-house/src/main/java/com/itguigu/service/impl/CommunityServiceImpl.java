package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.Community;
import com.itguigu.mapper.CommunityMapper;
import com.itguigu.mapper.DictMapper;
import com.itguigu.controller.CommunityService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    public BaseMapper<Community> getBaseMapper() {
        return communityMapper;
    }

//    @Override
//    public PageInfo<Community> findPage(Map filters) {
//        PageInfo<Community> page = super.findPage(filters);
//        List<Community> communityList = page.getList();
//        for (Community community : communityList) {
//            //设置区域名称
//            community.setAreaName(dictMapper.getNameById(community.getAreaId()));
//            //设置板块名称
//            community.setPlateName(dictMapper.getNameById(community.getPlateId()));
//        }
//        return page;
//    }


    @Override
    public List<Community> findAll() {
        return communityMapper.findAll();
    }
}
