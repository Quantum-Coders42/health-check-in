package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.mapper.WaterIntakeRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.CheckInRecordDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.WaterIntakeRecord;
import com.shiliuzi.healthcheckin.service.WaterIntakeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 饮水记录Service实现类
 */
@Service
public class WaterIntakeServiceImpl extends ServiceImpl<WaterIntakeRecordMapper, WaterIntakeRecord>
        implements WaterIntakeService {

    @Override
    public Long addRecord(CheckInRecordDto dto, Long userId) {
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
    public List<WaterIntakeRecord> getRecords(Long userId, RecordSelectDto dto) {
        // 支持按日期范围查询
        return lambdaQuery()
                .eq(WaterIntakeRecord::getUserId, userId)
                .orderByDesc(WaterIntakeRecord::getRecordDate)
                .ge(dto.getStartDate() != null, WaterIntakeRecord::getRecordDate, dto.getStartDate())
                .le(dto.getEndDate() != null, WaterIntakeRecord::getRecordDate, dto.getEndDate())
                .list();
    }
}