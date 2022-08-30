package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.Admin;
import com.itguigu.entity.HouseBroker;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokeController extends BaseController {

    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String SHOW_ACTION = "redirect:/house/";

    @Reference
    private AdminService adminService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @RequestMapping("/create")
    public String create(Long houseId, Model model){
        //经纪人 houseBroker
        HouseBroker houseBroker = new HouseBroker();
        houseBroker.setId(houseId);
        model.addAttribute("houseBroker", houseBroker);
        //用户 adminList
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList", adminList);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(HouseBroker houseBroker,Model model){
        Long brokerId = houseBroker.getBrokerId();
        //根据brokerId查找到Admin的name和url
        Admin admin = adminService.getById(brokerId);
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return successPage(model,"添加经纪人成功");
    }

    /**
     * 修改经纪人信息
     * @param id 经纪人id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        model.addAttribute("houseBroker", houseBroker);
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList", adminList);
        return PAGE_EDIT;
    }

    /**
     *
     * @param id 当前经纪人的id，主键值
     * @param brokerId 新经纪人的id （admin的id）
     * @return
     */
    @RequestMapping("/update/{id}")
    public String update(@PathVariable Long id,Long brokerId,Model model){
        HouseBroker houseBroker = new HouseBroker();
        //指定要修改哪个brokerId
        houseBroker.setId(id);
        Admin admin = adminService.getById(brokerId);
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return successPage(model , "修改经纪人成功!");
    }

    /**
     * 删除经纪人
     * @param houseId 房源id
     * @param id hse_house_broker的id
     * @return
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id,Model model){
        houseBrokerService.delete(id);
        //删除后重定向到house/show.html页面
        return SHOW_ACTION + houseId;
    }
}
