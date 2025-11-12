package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.pojo.dto.LoginDto;
import com.shiliuzi.healthcheckin.pojo.po.User;
import com.shiliuzi.healthcheckin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录接口
     * @param loginDto 登录信息
     * @return 登录结果，包含JWT token
     */
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginDto loginDto) {
        return Result.success(userService.login(loginDto));
    }
    
    /**
     * 用户注册接口
     * @param user 用户注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Boolean> register(@Valid @RequestBody User user) {
        boolean result = userService.register(user);
        return Result.success(result);
    }
}
