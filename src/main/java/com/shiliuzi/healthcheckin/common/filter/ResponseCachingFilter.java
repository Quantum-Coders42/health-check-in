package com.shiliuzi.healthcheckin.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Order(1) // 确保此 Filter 在其他 Filter (如 LogInterceptor) 之前运行
public class ResponseCachingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(httpServletResponse);

        try {
            chain.doFilter(request, responseWrapper);
        } finally {
            // 确保响应体被写回原始响应
            responseWrapper.copyBodyToResponse();
        }
    }

    // init 和 destroy 方法可以为空
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
