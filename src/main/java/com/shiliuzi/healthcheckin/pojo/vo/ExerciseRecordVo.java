package com.shiliuzi.healthcheckin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 运动记录VO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRecordVo {
    private Long id;
    private Long userId;
    private LocalDate recordDate;
    private String description;
    private Long exerciseTypeId;
    private String exerciseTypeName;
    private Integer duration;
    private Integer intensity;
}