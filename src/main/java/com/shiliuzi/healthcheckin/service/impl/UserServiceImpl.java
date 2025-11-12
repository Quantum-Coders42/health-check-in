package com.shiliuzi.healthcheckin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.UserMapper;
import com.shiliuzi.healthcheckin.pojo.dto.LoginDto;
import com.shiliuzi.healthcheckin.pojo.po.User;
import com.shiliuzi.healthcheckin.service.UserService;
import com.shiliuzi.healthcheckin.util.JwtUtil;
import com.shiliuzi.healthcheckin.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 用户登录方法
     * 根据用户名和密码进行登录验证
     * @param loginDto 包含用户名和密码的登录数据
     * @return JWT token字符串
     */
    @Override
    public String login(LoginDto loginDto) {
        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDto.getUsername());
        User user = getOne(queryWrapper);

        // 检查用户是否存在
        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        // 验证密码
        if (!PasswordUtil.verifyPassword(loginDto.getPassword(), user.getPassword())) {
            throw new ServiceException("密码错误");
        }

        // 生成JWT token
        return JwtUtil.makeToken(String.valueOf(user.getId()), secret);
    }
    
    /**
     * 用户注册方法
     * 检查用户名是否已存在，并对密码进行加密后保存
     * @param user 用户注册信息
     * @return 是否注册成功
     */
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User existingUser = getOne(queryWrapper);
        
        if (existingUser != null) {
            throw new ServiceException("用户名已存在");
        }
        
        // 对密码进行MD5加密
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        
        // 保存用户
        return save(user);
    }
}
