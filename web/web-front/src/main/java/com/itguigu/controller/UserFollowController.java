package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.UserInfo;
import com.itguigu.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/userFollow")
public class UserFollowController extends BaseController {


    @Reference
    private UserFollowService userFollowService;

    @ResponseBody
    @GetMapping("/auth/follow/{houseId}")
    public Result addFollow(@PathVariable Long houseId , HttpSession session){
        UserInfo existUserInfo = (UserInfo) session.getAttribute("USER");
        Long userId = existUserInfo.getId();
        userFollowService.addFollow(userId, houseId);
        return Result.ok();
    }

    @ResponseBody
    @GetMapping("/auth/cancelFollow/{houseId}")
    public Result cancelFollow(@PathVariable Long houseId , HttpSession session){
        UserInfo existUserInfo = (UserInfo) session.getAttribute("USER");
        Long userId = existUserInfo.getId();
        userFollowService.cancelFollow(userId, houseId);
        return Result.ok();
    }

}
