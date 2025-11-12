package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.dto.LoginDto;
import com.shiliuzi.healthcheckin.pojo.po.User;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param loginDto 登录信息
     * @return JWT token
     */
    String login(LoginDto loginDto);
    
    /**
     * 用户注册
     * @param user 用户信息
     * @return 是否注册成功
     */
    boolean register(LoginDto user);

}
