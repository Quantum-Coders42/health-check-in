package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.mapper.WaterIntakeRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.WaterIntakeCheckInDto;
import com.shiliuzi.healthcheckin.pojo.po.WaterIntakeRecord;
import com.shiliuzi.healthcheckin.service.WaterIntakeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 饮水记录Service实现类
 */
@Service
public class WaterIntakeServiceImpl extends ServiceImpl<WaterIntakeRecordMapper, WaterIntakeRecord>
        implements WaterIntakeService {

    @Override
    public Long addWaterIntakeRecord(WaterIntakeCheckInDto dto, Long userId) {
        // 构建实体对象
        WaterIntakeRecord record = new WaterIntakeRecord();
        BeanUtil.copyProperties(dto, record);
        record.setUserId(userId);
        record.setRecordDate(LocalDate.now());
        record.setCreatedAt(LocalDateTime.now());

        save(record);
        return record.getId();
    }

    @Override
    public List<WaterIntakeRecord> getWaterIntakeRecords(Long userId, LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .eq(WaterIntakeRecord::getUserId, userId)
                .orderByDesc(WaterIntakeRecord::getRecordDate)
                .orderByDesc(WaterIntakeRecord::getCreatedAt)
                .ge(startDate != null, WaterIntakeRecord::getRecordDate, startDate)
                .le(endDate != null, WaterIntakeRecord::getRecordDate, endDate)
                .list();
    }

    @Override
    public Integer getDailyWaterIntake(Long userId, LocalDate date) {
        List<WaterIntakeRecord> records = lambdaQuery()
                .eq(WaterIntakeRecord::getUserId, userId)
                .eq(WaterIntakeRecord::getRecordDate, date)
                .list();

        return records.stream()
                .map(WaterIntakeRecord::getAmountMl)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);
    }
}