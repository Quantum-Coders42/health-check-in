package com.shiliuzi.healthcheckin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 食物VO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodVo {
    private Long id;
    private String foodName;
    private Double calories;
    private Integer weight;
    private Long foodTypeId;
    private String foodTypeName;
}