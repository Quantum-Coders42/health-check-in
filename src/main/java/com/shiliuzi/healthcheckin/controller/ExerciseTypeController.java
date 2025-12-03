package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseType;
import com.shiliuzi.healthcheckin.service.ExerciseTypeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运动类型管理控制器
 */
@RestController
@RequestMapping("/exercise/types")
public class ExerciseTypeController {

    private final ExerciseTypeService exerciseTypeService;

    @Autowired
    public ExerciseTypeController(ExerciseTypeService exerciseTypeService) {
        this.exerciseTypeService = exerciseTypeService;
    }

    /**
     * 查询所有运动类型
     *
     * @return 运动类型列表
     */
    @GetMapping
    public Result<List<ExerciseType>> getAllTypes() {
        List<ExerciseType> types = exerciseTypeService.getAllTypes();
        return Result.success(types);
    }

    /**
     * 新增运动类型
     *
     * @param request 请求参数
     * @return 操作结果
     */
    @PostMapping
    public Result<Boolean> addType(@RequestBody @Validated AddTypeRequest request) {
        return Result.success(exerciseTypeService.addType(request.getTypeName()));
    }

    /**
     * 新增运动类型请求参数
     */
    @Data
    public static class AddTypeRequest {
        @NotBlank(message = "运动类型名称不能为空")
        @Size(max = 50, message = "运动类型名称不能超过50字")
        private String typeName;
    }
}