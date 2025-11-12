package com.shiliuzi.healthcheckin.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {

    public static final String CODE_SUCCESS = "200";
    public static final String CODE_AUTH_ERROR = "401";
    public static final String CODE_VALID_ERROR = "400";
    public static final String CODE_SYS_ERROR = "500";

    private String code;
    private String msg;
    private T data; // 将 Object 改为 T

    public static <T> Result<T> success() { // 添加类型参数 T 并指定返回类型
        return new Result<>(CODE_SUCCESS, "success", null);
    }

    public static <T> Result<T> success(T data) { // 添加类型参数 T 并指定返回类型
        return new Result<>(CODE_SUCCESS, "success", data);
    }

    public static <T> Result<T> error(String msg) { // 添加类型参数 T 并指定返回类型
        return new Result<>(CODE_SYS_ERROR, msg, null);
    }

    public static <T> Result<T> error(String code, String msg) { // 添加类型参数 T 并指定返回类型
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> error(AppExceptionCodeMsg appExceptionCodeMsg) {
        return new Result<>(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg(), null);
    }

    public static <T> Result<List<ValidationError>> validationError(List<ValidationError> errors) {
        return new Result<>(CODE_VALID_ERROR, "参数校验失败", errors);
    }

    public static <T> Result<T> authError(String msg) { // 添加类型参数 T 并指定返回类型
        return new Result<>(CODE_AUTH_ERROR, "认证错误", null);
    }
}
