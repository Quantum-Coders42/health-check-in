package com.shiliuzi.healthcheckin.common.interceptor;

import com.shiliuzi.healthcheckin.common.wrapper.RequestBodyWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        // 打印请求基本信息
        log.info(
                "========== 请求开始 ==========\n" + "\t请求URI: {}\n" + "\t请求方法: {}",
                request.getRequestURI(),
                request.getMethod());

        // 打印请求参数
        Map<String, String[]> paramMap = request.getParameterMap();
        if (!paramMap.isEmpty()) {
            StringBuilder params = new StringBuilder("请求参数:\n");
            paramMap.forEach(
                    (key, values) -> {
                        params.append(String.format("\t%s=%s\n", key, String.join(",", values)));
                    });
            log.info(params.toString().trim()); // Trim trailing newline
        } else {
            log.info("请求参数:\n\t无参数");
        }

        // 打印请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames.hasMoreElements()) {
            StringBuilder headers = new StringBuilder("请求头:\n");
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.append(
                        String.format("\t%s=%s\n", headerName, request.getHeader(headerName)));
            }
            log.info(headers.toString().trim()); // Trim trailing newline
        } else {
            log.info("请求头:\n\t无请求头");
        }

        // 打印请求体
        StringBuilder requestBodyLog = new StringBuilder("请求体:\n");
        if (request instanceof RequestBodyWrapper) {
            String contentType = request.getContentType();
            RequestBodyWrapper requestWrapper = (RequestBodyWrapper) request;
            byte[] bodyBytes = requestWrapper.getBody();
            if (bodyBytes != null && bodyBytes.length > 0) {
                // 如果是文本类型的内容，直接打印
                if (contentType != null
                        && (contentType.contains(MediaType.APPLICATION_JSON_VALUE)
                                || contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                || contentType.contains(MediaType.TEXT_PLAIN_VALUE)
                                || contentType.contains(MediaType.TEXT_HTML_VALUE))) {
                    requestBodyLog.append("\t").append(new String(bodyBytes));
                } else {
                    requestBodyLog.append(
                            String.format("\t[二进制内容或其他格式，长度: %d bytes]", bodyBytes.length));
                }
            } else {
                requestBodyLog.append("\t无请求体");
            }
        } else {
            requestBodyLog.append("\t无法获取请求体，请确保已配置RequestBodyCachingFilter");
        }
        log.info(requestBodyLog.toString());

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {
        // 在Controller处理完请求后调用，可以访问ModelAndView
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime != null) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            StringBuilder completionLog = new StringBuilder();
            completionLog.append("响应状态: ").append(response.getStatus()).append("\n");

            // 打印响应体（如果可用）
            if (response instanceof ContentCachingResponseWrapper) {
                ContentCachingResponseWrapper responseWrapper =
                        (ContentCachingResponseWrapper) response;
                byte[] content = responseWrapper.getContentAsByteArray();
                if (content.length > 0) {
                    String body = new String(content, StandardCharsets.UTF_8);
                    int lengthToLog = Math.min(body.length(), 255); // Limit log size
                    completionLog
                            .append("\t响应体 (前 ")
                            .append(lengthToLog)
                            .append(" 字符): ")
                            .append(body.substring(0, lengthToLog))
                            .append("\n"); // Add newline
                    completionLog.append("\t完整响应长度: ").append(content.length).append(" bytes");
                } else {
                    completionLog.append("\t响应体为空");
                }
            } else {
                // 尝试获取响应长度 (作为后备)
                String contentLength = response.getHeader("Content-Length");
                if (contentLength != null) {
                    completionLog
                            .append("\t响应长度 (从Header): ")
                            .append(contentLength)
                            .append(" bytes");
                } else {
                    completionLog.append("\t响应长度: 未知 (无法读取响应体)");
                }
            }
            // Log combined response details
            log.info(completionLog.toString().trim()); // Trim potential trailing newline

            // Log exception separately if it exists
            if (ex != null) {
                log.error("请求处理异常: ", ex);
            }

            // Log timing and end marker together
            log.info("请求耗时: {}ms\n========== 请求结束 ==========", executionTime);
        }
    }
}
