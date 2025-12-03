package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 运动打卡DTO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseCheckInDto {

    /**
     * 运动类型ID
     */
    @NotNull(message = "运动类型不能为空")
    private Long exerciseTypeId;

    /**
     * 运动时长（分钟）
     */
    @NotNull(message = "运动时长不能为空")
    @Min(value = 1, message = "运动时长至少为1分钟")
    private Integer duration;

    /**
     * 运动强度（1-5）
     */
    @NotNull(message = "运动强度不能为空")
    @Min(value = 1, message = "运动强度最小为1")
    @Max(value = 5, message = "运动强度最大为5")
    private Integer intensity;

    /**
     * 运动描述
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;
}