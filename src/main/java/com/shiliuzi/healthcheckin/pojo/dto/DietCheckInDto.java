package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 饮食打卡DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietCheckInDto {

    /**
     * 饮食类型：BREAKFAST-早餐，LUNCH-午餐，DINNER-晚餐，SNACK-加餐
     */
    @NotBlank(message = "饮食类型不能为空")
    @Pattern(regexp = "^(BREAKFAST|LUNCH|DINNER|SNACK)$", message = "饮食类型必须是：BREAKFAST、LUNCH、DINNER或SNACK")
    private String mealType;

    /**
     * 食物描述
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;

    /**
     * 卡路里估算（可选）
     */
    private Integer calories;
}