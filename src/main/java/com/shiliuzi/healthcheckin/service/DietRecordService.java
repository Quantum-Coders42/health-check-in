package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.dto.CheckInRecordDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.DietRecord;

import java.time.LocalDate;
import java.util.List;

/**
 * 饮食打卡记录Service接口
 */
public interface DietRecordService extends IService<DietRecord> {

    /**
     * 新增饮食打卡记录
     *
     * @param dto 打卡信息
     * @param userId 用户ID
     * @return 记录ID
     */
    Long addRecord(CheckInRecordDto dto, Long userId);

    /**
     * 获取饮食记录列表
     *
     * @param userId 用户ID
     * @param dto 查询条件
     * @return 记录列表
     */
    List<DietRecord> getRecords(Long userId, RecordSelectDto dto);
}