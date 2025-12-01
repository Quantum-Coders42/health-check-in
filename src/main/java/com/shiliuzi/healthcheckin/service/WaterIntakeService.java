package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.dto.WaterIntakeCheckInDto;
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
    Long addWaterIntakeRecord(WaterIntakeCheckInDto dto, Long userId);

    /**
     * 获取饮水记录列表
     *
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 记录列表
     */
    List<WaterIntakeRecord> getWaterIntakeRecords(Long userId, LocalDate startDate, LocalDate endDate);

    /**
     * 获取每日总饮水量
     *
     * @param userId 用户ID
     * @param date 查询日期
     * @return 当日总饮水量（毫升）
     */
    Integer getDailyWaterIntake(Long userId, LocalDate date);
}