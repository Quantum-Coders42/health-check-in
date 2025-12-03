package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.MealType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用餐类型Mapper接口
 */
@Mapper
public interface MealTypeMapper extends BaseMapper<MealType> {
}