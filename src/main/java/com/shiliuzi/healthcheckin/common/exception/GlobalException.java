package com.shiliuzi.healthcheckin.common.exception;


import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.ValidationError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chun
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalException extends Exception {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result ServiceException(ServiceException e) {
        log.warn("ServiceException:{}", e.getMessage());
        // 打印错误调用栈
        log.warn("ServiceException:", e);
        return Result.error(e.getCode(), e.getMessage());
    }

    // 新增：处理请求体参数校验异常（@RequestBody + @Valid）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<List<ValidationError>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<ValidationError> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        new ValidationError(
                                                fieldError.getField(),
                                                fieldError.getRejectedValue(),
                                                fieldError.getDefaultMessage()))
                        .toList();
        log.warn("参数校验失败: {}", errors);
        return Result.validationError(errors);
    }

    // 新增：处理 URL/Query 参数校验异常（@RequestParam + @Validated）
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<List<ValidationError>> handleConstraintViolation(
            ConstraintViolationException ex) {
        List<ValidationError> errors =
                ex.getConstraintViolations().stream()
                        .map(
                                violation -> {
                                    String field =
                                            violation
                                                    .getPropertyPath()
                                                    .toString()
                                                    .split("\\.")[2]; // 提取字段名
                                    return new ValidationError(
                                            field,
                                            violation.getInvalidValue(),
                                            violation.getMessage());
                                })
                        .toList();
        log.warn("参数校验失败: {}", errors);
        return Result.validationError(errors);
    }
}
