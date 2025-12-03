package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.pojo.po.FoodType;
import com.shiliuzi.healthcheckin.pojo.po.MealType;
import com.shiliuzi.healthcheckin.service.FoodTypeService;
import com.shiliuzi.healthcheckin.service.MealTypeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 饮食类型管理控制器
 */
@RestController
@RequestMapping("/diet/types")
public class DietTypeController {

    private final MealTypeService mealTypeService;
    private final FoodTypeService foodTypeService;

    @Autowired
    public DietTypeController(MealTypeService mealTypeService, FoodTypeService foodTypeService) {
        this.mealTypeService = mealTypeService;
        this.foodTypeService = foodTypeService;
    }

    /**
     * 查询所有用餐类型
     *
     * @return 用餐类型列表
     */
    @GetMapping("/meal-types")
    public Result<List<MealType>> getAllMealTypes() {
        List<MealType> mealTypes = mealTypeService.getAllTypes();
        return Result.success(mealTypes);
    }

    /**
     * 新增用餐类型
     *
     * @param request 请求参数
     * @return 操作结果
     */
    @PostMapping("/meal-type")
    public Result<Boolean> addMealType(@RequestBody @Validated AddMealTypeRequest request) {
        return Result.success(mealTypeService.addType(request.getTypeName()));

    }

    /**
     * 查询所有食物类型
     *
     * @return 食物类型列表
     */
    @GetMapping("/food-types")
    public Result<List<FoodType>> getAllFoodTypes() {
        List<FoodType> foodTypes = foodTypeService.getAllTypes();
        return Result.success(foodTypes);
    }

    /**
     * 新增食物类型
     *
     * @param request 请求参数
     * @return 操作结果
     */
    @PostMapping("/food-type")
    public Result<Boolean> addFoodType(@RequestBody @Validated AddFoodTypeRequest request) {
        return Result.success(foodTypeService.addType(request.getTypeName()));
    }

    /**
     * 新增用餐类型请求参数
     */
    @Data
    public static class AddMealTypeRequest {
        @NotBlank(message = "用餐类型名称不能为空")
        @Size(max = 50, message = "用餐类型名称不能超过50字")
        private String typeName;
    }

    /**
     * 新增食物类型请求参数
     */
    @Data
    public static class AddFoodTypeRequest {
        @NotBlank(message = "食物类型名称不能为空")
        @Size(max = 50, message = "食物类型名称不能超过50字")
        private String typeName;
    }
}