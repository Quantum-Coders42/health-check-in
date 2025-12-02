package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.DietRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 饮食打卡记录Mapper接口
 */
@Mapper
public interface DietRecordMapper extends BaseMapper<DietRecord> {
}