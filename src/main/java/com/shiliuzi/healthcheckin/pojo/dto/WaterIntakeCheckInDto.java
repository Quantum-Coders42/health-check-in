package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
     * 饮水体积（毫升）
     */
    @NotNull(message = "饮水体积不能为空")
    @Min(value = 1, message = "饮水体积至少为1毫升")
    @Max(value = 5000, message = "饮水体积不能超过5000毫升")
    private Integer volume;

    /**
     * 饮水描述
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;
}