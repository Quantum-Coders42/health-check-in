package com.shiliuzi.healthcheckin.common.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String secret;

    // 目标方法执行之前
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("/error".equals(request.getRequestURI())) {
            return true;
        }
        // 从 Authorization 头中获取 token
        String token = request.getHeader("Authorization");
        if (token == null) {
            // 未登录
            throw new ServiceException("40100", "请重新登录");
        }
        if (token.length() > 7) {
            token = token.substring(7);
        }
        // 验证 token 的有效性
        try {
            if (!JwtUtil.testToken(token, secret) || JwtUtil.getUserIdByToken(token) == null) {
                throw new ServiceException("40100", "登录已过期，请重新登录");
            }
        } catch (TokenExpiredException e) {
            throw new ServiceException("40100", "登录已过期，请重新登录");
        } catch (Exception e) {
            throw new ServiceException("40100", "token验证失败，请重新登录");
        }
        // 获取 token 中的 user id
        Long userId = Long.parseLong(Objects.requireNonNull(JwtUtil.getUserIdByToken(token)));
        request.setAttribute("userId", userId);
        return true;
    }

    // 目标方法执行之后
    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 页面渲染之后
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public static Long getUserIdFromReq(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }
}
