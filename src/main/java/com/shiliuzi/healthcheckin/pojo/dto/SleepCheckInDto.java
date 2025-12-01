package com.shiliuzi.healthcheckin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 睡眠打卡DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepCheckInDto {

    /**
     * 入睡时间
     */
    @NotNull(message = "入睡时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sleepTime;

    /**
     * 起床时间
     */
    @NotNull(message = "起床时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime wakeTime;

    /**
     * 睡眠质量描述
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;
}