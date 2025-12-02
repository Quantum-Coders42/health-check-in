package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.dto.CheckInRecordDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.WaterIntakeRecord;

import java.time.LocalDate;
import java.util.List;

/**
 * 饮水记录Service接口
 */
public interface WaterIntakeService extends IService<WaterIntakeRecord> {

    /**
     * 新增饮水记录
     *
     * @param dto 打卡信息
     * @param userId 用户ID
     * @return 记录ID
     */
    Long addRecord(CheckInRecordDto dto, Long userId);

    /**
     * 获取饮水记录列表
     *
     * @param userId 用户ID
     * @param dto 查询条件
     * @return 记录列表
     */
    List<WaterIntakeRecord> getRecords(Long userId, RecordSelectDto dto);
}