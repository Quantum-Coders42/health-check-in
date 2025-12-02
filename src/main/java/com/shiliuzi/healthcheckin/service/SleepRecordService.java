package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.dto.CheckInRecordDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.SleepRecord;

import java.time.LocalDate;
import java.util.List;

/**
 * 睡眠打卡记录Service接口
 */
public interface SleepRecordService extends IService<SleepRecord> {

    /**
     * 新增睡眠打卡记录
     *
     * @param dto 打卡信息
     * @param userId 用户ID
     * @return 记录ID
     */
    Long addRecord(CheckInRecordDto dto, Long userId);

    /**
     * 获取睡眠记录列表
     *
     * @param userId 用户ID
     * @param dto 查询条件
     * @return 记录列表
     */
    List<SleepRecord> getRecords(Long userId, RecordSelectDto dto);
}