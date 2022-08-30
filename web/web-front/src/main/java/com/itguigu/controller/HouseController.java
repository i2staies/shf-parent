package com.itguigu.controller;

import com.github.pagehelper.PageInfo;
import com.itguigu.base.BaseController;
import com.itguigu.entity.Community;
import com.itguigu.entity.House;
import com.itguigu.entity.HouseBroker;
import com.itguigu.entity.HouseImage;
import com.itguigu.entity.bo.HouseQueryBo;
import com.itguigu.entity.vo.HouseVo;
import com.itguigu.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;

    @ResponseBody
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result list(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestBody HouseQueryBo houseQueryBo){
        PageInfo<HouseVo> pageInfo = houseService.findByPage(pageNum,pageSize,houseQueryBo);
        return Result.ok(pageInfo);
    }

    @ResponseBody
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id, Model model){
        //house
        House house = houseService.getById(id);
        //community
        Community community = communityService.getById(house.getCommunityId());
        //houseBrokerList
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(house.getId());
        //houseImage1List
        List<HouseImage> houseImage1List = houseImageService.findListByTypeAndHouseId(id, 1);
        //isFollow
        boolean isFollow = false;
        Map map = new HashMap();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        map.put("isFollow",isFollow);
        return Result.ok(map);
    }

}
