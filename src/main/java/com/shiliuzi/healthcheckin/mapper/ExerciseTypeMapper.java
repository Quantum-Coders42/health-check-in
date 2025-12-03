package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运动类型Mapper接口
 */
@Mapper
public interface ExerciseTypeMapper extends BaseMapper<ExerciseType> {
}