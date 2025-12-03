package com.shiliuzi.healthcheckin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 饮水记录VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaterIntakeRecordVo {
    private Long id;
    private Long userId;
    private LocalDate recordDate;
    private Integer volume;
    private String description;
    private LocalDateTime createdAt;
}