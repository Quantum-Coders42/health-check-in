package com.shiliuzi.healthcheckin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 饮食记录VO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietRecordVo {
    private Long id;
    private Long userId;
    private LocalDate recordDate;
    private String description;
    private LocalDateTime createdAt;
    private Long mealTypeId;
    private String mealTypeName;
    private List<FoodVo> foods;
}