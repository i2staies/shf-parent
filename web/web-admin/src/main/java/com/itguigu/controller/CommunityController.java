package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.Community;
import com.itguigu.entity.Dict;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {


    private static final String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";
    private static final String LIST_ACTION = "redirect:/community";

    @Reference
    private DictService dictService;
    @Reference
    private CommunityService communityService;


    /**
     * 获取区域列表并保存到请求
     * @param model
     */
    private void getAreaListToRequestScope(Model model) {
        List<Dict> areaList = dictService.findListByParentId(110000L);
        model.addAttribute("areaList", areaList);
    }


    /**
     * 跳转'community/index.html'页面
     * @param filters : name(小区名称) , areaId(区域id) , plateId(板块id)，pageNum，pageSize
     * @return
     */
    @RequestMapping
    public String index(@RequestParam Map filters, Model model) {
        //areaId，plateId设置初始值
        if (!filters.containsKey("areaId")) {
            filters.put("areaId", "");
        }

        if (!filters.containsKey("plateId")) {
            filters.put("plateId", "");
        }

        //数据回显
        model.addAttribute("filters", filters);
        //查询区域列表
        getAreaListToRequestScope(model);
        //分页查询
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        model.addAttribute("page", pageInfo);
        return PAGE_INDEX;
    }


    /**
     * 跳转'community/create.html'页面
     *
     * 新增小区信息
     * @return
     */
    @RequestMapping("/create")
    public String create(Model model) {
        //需要提供区域列表给model，create页面需要选择区域
        getAreaListToRequestScope(model);
        return PAGE_CREATE;
    }


    /**
     * 添加小区
     *
     * @param community
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(Community community, Model model) {
        communityService.insert(community);
        return successPage(model, "添加小区成功!");
    }


    /**
     * 跳转'community/edit.html'
     *
     * @param id : 小区id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        //获取小区信息
        Community community = communityService.getById(id);
        model.addAttribute("community", community);
        //获取区域列表
        getAreaListToRequestScope(model);
        return PAGE_EDIT;
    }


    /**
     * 修改小区
     * @param community
     * @param model
     * @return
     */
    @PostMapping("/update")
    public String update(Community community, Model model) {
        communityService.update(community);
        return successPage(model, "修改小区成功!");
    }


    /**
     * 删除小区
     * @param id : 小区id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        communityService.delete(id);
        return LIST_ACTION;
    }




}
