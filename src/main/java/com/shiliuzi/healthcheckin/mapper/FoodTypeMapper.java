package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.FoodType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食物类型Mapper接口
 */
@Mapper
public interface FoodTypeMapper extends BaseMapper<FoodType> {
}