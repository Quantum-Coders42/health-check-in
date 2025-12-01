package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.SleepRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.SleepCheckInDto;
import com.shiliuzi.healthcheckin.pojo.po.SleepRecord;
import com.shiliuzi.healthcheckin.service.SleepRecordService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 睡眠打卡记录Service实现类
 */
@Service
public class SleepRecordServiceImpl extends ServiceImpl<SleepRecordMapper, SleepRecord>
        implements SleepRecordService {

    @Override
    public Long addSleepRecord(SleepCheckInDto dto, Long userId) {
        // 检查是否已存在同一天的睡眠打卡记录
        List<SleepRecord> records = lambdaQuery()
                .eq(SleepRecord::getUserId, userId)
                .eq(SleepRecord::getRecordDate, LocalDate.now())
                .list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.SLEEP_RECORD_EXIST);
        }

        // 验证时间合理性
        if (dto.getWakeTime().isBefore(dto.getSleepTime())) {
            throw new ServiceException(AppExceptionCodeMsg.INVALID_SLEEP_TIME);
        }

        // 构建实体对象
        SleepRecord record = new SleepRecord();
        BeanUtil.copyProperties(dto, record);
        record.setUserId(userId);
        record.setRecordDate(LocalDate.now());
        record.setCreatedAt(LocalDateTime.now());

        // 计算睡眠时长（分钟）
        Duration duration = Duration.between(dto.getSleepTime(), dto.getWakeTime());
        record.setSleepDurationMinutes((int) duration.toMinutes());

        save(record);
        return record.getId();
    }

    @Override
    public List<SleepRecord> getSleepRecords(Long userId, LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .eq(SleepRecord::getUserId, userId)
                .orderByDesc(SleepRecord::getRecordDate)
                .ge(startDate != null, SleepRecord::getRecordDate, startDate)
                .le(endDate != null, SleepRecord::getRecordDate, endDate)
                .list();
    }
}