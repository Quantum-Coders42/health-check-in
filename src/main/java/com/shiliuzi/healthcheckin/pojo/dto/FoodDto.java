package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 食物DTO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

    /**
     * 食物名称
     */
    @Size(max = 100, message = "食物名称不能超过100字")
    @NotBlank(message = "食物名称不能为空")
    private String foodName;

    /**
     * 热量（卡路里）
     */
    private Double calories;

    /**
     * 重量（克）
     */
    private Integer weight;

    /**
     * 食物类型ID
     */
    @NotNull(message = "食物类型不能为空")
    private Long foodTypeId;
}