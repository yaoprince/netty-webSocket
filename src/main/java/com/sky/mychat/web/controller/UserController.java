package com.sky.mychat.web.controller;

import com.sky.mychat.common.CommonResult;
import com.sky.mychat.pojo.dto.LoginResult;
import com.sky.mychat.pojo.dto.RegisterAndLoginParam;
import com.sky.mychat.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tiankong
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public CommonResult register(@Validated @RequestBody RegisterAndLoginParam param) {
        LoginResult loginResult = userService.register(param);
        return CommonResult.success("注册成功!", loginResult);
    }

    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody RegisterAndLoginParam param) {
        return CommonResult.success("成功成功!", userService.login(param));
    }
}
