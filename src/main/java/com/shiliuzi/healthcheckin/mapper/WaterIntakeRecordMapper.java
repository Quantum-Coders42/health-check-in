package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.WaterIntakeRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 饮水记录Mapper接口
 */
@Mapper
public interface WaterIntakeRecordMapper extends BaseMapper<WaterIntakeRecord> {
}