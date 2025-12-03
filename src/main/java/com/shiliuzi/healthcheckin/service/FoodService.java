package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.dto.FoodDto;
import com.shiliuzi.healthcheckin.pojo.po.Food;
import com.shiliuzi.healthcheckin.pojo.vo.FoodVo;

import java.util.List;

/**
 * 食物Service接口
 */
public interface FoodService extends IService<Food> {

    /**
     * 批量保存食物信息
     *
     * @param foodDtos 食物DTO列表
     * @param dietRecordId 饮食记录ID
     * @return 食物VO列表
     */
    List<FoodVo> saveFoods(List<FoodDto> foodDtos, Long dietRecordId);

    /**
     * 根据饮食记录ID查询食物列表
     *
     * @param dietRecordId 饮食记录ID
     * @return 食物VO列表
     */
    List<FoodVo> getFoodsByDietRecordId(Long dietRecordId);
}