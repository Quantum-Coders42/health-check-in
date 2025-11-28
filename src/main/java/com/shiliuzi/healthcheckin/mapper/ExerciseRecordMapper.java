package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运动打卡记录Mapper接口
 */
@Mapper
public interface ExerciseRecordMapper extends BaseMapper<ExerciseRecord> {
}