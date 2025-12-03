package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 睡眠打卡DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepCheckInDto {
    /**
     * 睡眠时长（分钟）
     */
    @NotNull(message = "睡眠时长不能为空")
    @Min(value = 1, message = "睡眠时长至少为1分钟")
    @Max(value = 1440, message = "睡眠时长不能超过1440分钟（24小时）")
    private Integer duration;

    /**
     * 睡眠质量（1-5）
     */
    @NotNull(message = "睡眠质量不能为空")
    @Min(value = 1, message = "睡眠质量最小为1")
    @Max(value = 5, message = "睡眠质量最大为5")
    private Integer quality;

    /**
     * 睡眠描述
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;
}