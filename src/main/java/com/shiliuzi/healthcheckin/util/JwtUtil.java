package com.shiliuzi.healthcheckin.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static UserService staticUserService;

    @Resource private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    // 生成token
    public static String makeToken(String userId, String sign) {
        return JWT.create()
                .withAudience(userId) // userId为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 8)) // 8小时后token到期
                .sign(Algorithm.HMAC256(sign)); // username为token密钥
    }

    // 根据token查询用户id
    public static String getUserIdByToken(String token) {
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            throw new ServiceException(AppExceptionCodeMsg.TOKEN_TEST_ERROR);
        }
        return userId;
    }

    // 验证token
    public static boolean testToken(String token, String secret) {
        try {
            // 创建 JWT 验证器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            // 验证令牌
            jwtVerifier.verify(token);
            return true;
        } catch (JWTDecodeException e) {
            // 处理异常或记录日志
            return false;
        }
    }
}
