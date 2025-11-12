package com.shiliuzi.healthcheckin.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 参数校验错误处理
 * @author: chun
 */
@Data
@AllArgsConstructor
public class ValidationError {
    // 错误字段名
    private String field;
    // 前端传递的错误值
    private Object rejectedValue;
    // 错误原因（如"不能为空"）
    private String reason;
}
