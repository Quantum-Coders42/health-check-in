package com.shiliuzi.healthcheckin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 睡眠记录VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepRecordVo {
    private Long id;
    private Long userId;
    private LocalDate recordDate;
    private Integer duration;
    private Integer quality;
    private String description;
    private LocalDateTime createdAt;
}