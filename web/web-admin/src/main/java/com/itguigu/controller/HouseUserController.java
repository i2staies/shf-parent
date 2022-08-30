package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.HouseUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {

    @Reference
    private HouseUserService houseUserService;

    private static final String PAGE_EDIT = "houseUser/edit";

    private static final String PAGE_CREATE = "houseUser/create";

    private static final String SHOW_ACTION = "redirect:/house/";

    @RequestMapping("/create")
    public String create(HouseUser houseUser, Model model){
        model.addAttribute("houseUser", houseUser);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(HouseUser houseUser,Model model){
        houseUserService.insert(houseUser);
        return successPage(model,"添加房东成功！");
    }

    @GetMapping("/edit{id}")
    public String edit(@PathVariable Long id,Model model){
        HouseUser houseUser = new HouseUser();
        houseUserService.update(houseUser);
        return PAGE_EDIT;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, HouseUser houseUser,Model model){
        houseUser.setId(id);
        houseUserService.update(houseUser);

        return successPage(model,"修改房东信息成功");
    }


    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){
        houseUserService.delete(id);
        return SHOW_ACTION + houseId;
    }
}
