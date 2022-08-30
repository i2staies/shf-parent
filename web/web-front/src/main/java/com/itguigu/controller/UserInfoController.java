package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.UserInfo;
import com.itguigu.entity.bo.LoginBo;
import com.itguigu.entity.bo.RegisterBo;
import com.itguigu.result.Result;
import com.itguigu.result.ResultCodeEnum;
import com.itguigu.util.MD5;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {


    @Reference
    private UserInfoService userInfoService;

    //发送验证码
    @GetMapping("/sendCode/{mobile}")
    public Result sendCode(@PathVariable("mobile") String mobile, HttpSession session) {
        //这里本来需要调用第三方云短信的API给mobile发送短信
        //模拟短信验证码
        String code = "1111";
        //将短信存储到会话域session中用于校验
        session.setAttribute("CODE", code);
        //响应发送成功
        return Result.ok();
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession session) {
        //校验验证码是否正确
        if (!registerBo.getCode().equals(session.getAttribute("CODE"))) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        //校验手机号是否存在
        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());
        if (userInfo != null) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //密码加密
        registerBo.setPassword(MD5.encrypt(registerBo.getPassword()));
        userInfo = new UserInfo();
        BeanUtils.copyProperties(registerBo, userInfo);
        userInfo.setStatus(1);
        //注册
        userInfoService.insert(userInfo);

        return Result.ok();
    }

    //登录验证
    @PostMapping("/login")
    public Result login(@RequestBody LoginBo loginBo, HttpSession session){
        //判断手机号是否正确
        UserInfo userInfo = userInfoService.getByPhone(loginBo.getPhone());
        if (userInfo == null) {
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        //判断密码是否正确
        if (!MD5.encrypt(loginBo.getPassword()).equals(userInfo.getPassword())) {
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }

        //校验账户是否被禁用
        if (userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //登录成，将用户信息存储到session中
        session.setAttribute("USER",userInfo);

        //封装响应数据
        return Result.ok(userInfo);
    }

    @GetMapping("/logout")
    public Result logout(HttpSession session){
        //销毁session，当前用户保存的数据全部销毁
        session.invalidate();
        return Result.ok();
    }
}

