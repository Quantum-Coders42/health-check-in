package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.WaterIntakeRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.dto.WaterIntakeCheckInDto;
import com.shiliuzi.healthcheckin.pojo.po.WaterIntakeRecord;
import com.shiliuzi.healthcheckin.pojo.vo.WaterIntakeRecordVo;
import com.shiliuzi.healthcheckin.service.WaterIntakeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 饮水记录Service实现类
 */
@Service
public class WaterIntakeServiceImpl extends ServiceImpl<WaterIntakeRecordMapper, WaterIntakeRecord>
        implements WaterIntakeService {

    @Override
    @Transactional
    public Long addRecord(WaterIntakeCheckInDto dto, Long userId) {
        // 检查当天是否已存在饮水打卡记录
        List<WaterIntakeRecord> records = lambdaQuery()
                .eq(WaterIntakeRecord::getUserId, userId)
                .eq(WaterIntakeRecord::getRecordDate, LocalDate.now())
                .list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.WATER_RECORD_EXIST);
        }

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
    public List<WaterIntakeRecordVo> getRecords(Long userId, RecordSelectDto dto) {
        // 支持按日期范围查询
        List<WaterIntakeRecord> records = lambdaQuery()
                .eq(WaterIntakeRecord::getUserId, userId)
                .orderByDesc(WaterIntakeRecord::getRecordDate)
                .ge(dto.getStartDate() != null, WaterIntakeRecord::getRecordDate, dto.getStartDate())
                .le(dto.getEndDate() != null, WaterIntakeRecord::getRecordDate, dto.getEndDate())
                .list();

        if (records.isEmpty()) {
            return List.of();
        }

        // 转换为VO对象
        return records.stream()
                .map(record -> {
                    WaterIntakeRecordVo vo = new WaterIntakeRecordVo();
                    BeanUtil.copyProperties(record, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }
}