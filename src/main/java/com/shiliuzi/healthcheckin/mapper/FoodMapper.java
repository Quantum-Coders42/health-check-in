package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.Food;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食物Mapper接口
 */
@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}