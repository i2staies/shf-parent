package com.itguigu.controller;

import com.github.pagehelper.PageInfo;
import com.itguigu.base.BaseController;
import com.itguigu.entity.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_SHOW = "house/show";

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseService houseService;

    @Reference
    private HouseUserService houseUserService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @RequestMapping
    public String index(Map filters, Model model){
        //数据回显
        model.addAttribute("filters", filters);
        getListToRequestScope(model);
        //分页查询
        PageInfo<House> page = houseService.findPage(filters);
        model.addAttribute("page", page);
        return PAGE_INDEX;
    }

    /**
     * 获取小区列表
     *    户型列表
     *    楼层列表
     *    建筑结构列表
     *    朝向列表
     *    装修情况列表
     *    房屋用途列表
     * @param model
     */
    private void getListToRequestScope(Model model) {
        //小区列表 communityList
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList",communityList);
        //户型列表houseTypeList  (按dict_code查找户型id，在根据parent_id查找户型列表)
        List<Dict> houseTypeList = dictService.findListByParentCode("houseType");
        model.addAttribute("houseTypeList",houseTypeList);
        //楼层列表
        List<Dict> floorList = dictService.findListByParentCode("floor");
        model.addAttribute("floorList",floorList);
        //建筑结构列表
        List<Dict> buildStructureList = dictService.findListByParentCode("buildStructure");
        model.addAttribute("buildStructureList",buildStructureList);
        //房屋朝向列表
        List<Dict> directionList = dictService.findListByParentCode("direction");
        model.addAttribute("directionList",directionList);
        //房屋用途列表
        List<Dict> houseUseList = dictService.findListByParentCode("houseUse");
        model.addAttribute("houseUseList",houseUseList);
        //装修情况列表
        List<Dict> decorationList = dictService.findListByParentCode("decoration");
        model.addAttribute("decorationList",decorationList);
    }

    @GetMapping("/create")
    public String create(Model model){
        getListToRequestScope(model);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(House house,Model model){
        houseService.insert(house);
        return successPage(model,"添加房源成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        getListToRequestScope(model);

        return PAGE_EDIT;
    }

    /**
     * 修改房源
     * @param house 传递过来的参数
     * @param model
     * @return
     */
    @PostMapping("/update")
    public String edit(House house,Model model){
        houseService.update(house);
        return successPage(model,"修改房源成功");
    }

    /**
     * 删除房源
     * @param id 房源id
     * @return
     */
    @GetMapping("/delete")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 发布房源
     * @param id 房源id
     * @param status 需要修改的房源装态
     * @return
     */
    @GetMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id,@PathVariable Integer status){
        houseService.publish(id,status);
        return LIST_ACTION;
    }

    /**
     * 跳转到房源详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String show(@PathVariable Long id,Model model){
        //房源信息 house
        House house = houseService.getById(id);
        model.addAttribute("house", house);
        //小区信息 community
        Community community = communityService.getById(house.getCommunityId());
        model.addAttribute("community", community);
        //房源图片列表 houseImage1List   type=1
        List<HouseImage> houseImage1List = houseImageService.findListByTypeAndHouseId(id, 1);
        model.addAttribute("houseImage1List", houseImage1List);
        //房产图片列表 houseImage2List  需要house_id   type=2
        List<HouseImage> houseImage2List = houseImageService.findListByTypeAndHouseId(id, 2);
        model.addAttribute("houseImage2List", houseImage2List);
        //经纪人列表 houseBrokerList
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        model.addAttribute("houseBrokerList", houseBrokerList);
        //房东列表 houseUserList
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(id);
        model.addAttribute("houseUserList", houseUserList);
        return PAGE_SHOW;
    }
}
