package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 饮水打卡DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaterIntakeCheckInDto {

    /**
     * 饮水量（毫升）
     */
    @NotNull(message = "饮水量不能为空")
    @Positive(message = "饮水量必须大于0")
    private Integer amountMl;

    /**
     * 饮水备注
     */
    @Size(max = 200, message = "备注不能超过200字")
    private String description;
}