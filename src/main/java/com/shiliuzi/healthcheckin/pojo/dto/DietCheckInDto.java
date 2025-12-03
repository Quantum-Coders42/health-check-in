package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 饮食打卡DTO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietCheckInDto {

    /**
     * 用餐类型ID
     */
    @NotNull(message = "用餐类型不能为空")
    private Long mealTypeId;

    /**
     * 食物列表（可为null）
     */
    @Valid
    private List<FoodDto> foods;

    /**
     * 饮食描述
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;
}