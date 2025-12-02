package com.shiliuzi.healthcheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.healthcheckin.pojo.po.SleepRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 睡眠打卡记录Mapper接口
 */
@Mapper
public interface SleepRecordMapper extends BaseMapper<SleepRecord> {
}