package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.SleepRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.dto.SleepCheckInDto;
import com.shiliuzi.healthcheckin.pojo.po.SleepRecord;
import com.shiliuzi.healthcheckin.pojo.vo.SleepRecordVo;
import com.shiliuzi.healthcheckin.service.SleepRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 睡眠打卡记录Service实现类
 */
@Service
public class SleepRecordServiceImpl extends ServiceImpl<SleepRecordMapper, SleepRecord>
        implements SleepRecordService {

    @Override
    @Transactional
    public Long addRecord(SleepCheckInDto dto, Long userId) {
        // 检查同一天是否已存在打卡记录
        List<SleepRecord> records = lambdaQuery()
                .eq(SleepRecord::getUserId, userId)
                .eq(SleepRecord::getRecordDate, LocalDate.now())
                .list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.SLEEP_RECORD_EXIST);
        }

        // 构建实体对象
        SleepRecord record = new SleepRecord();
        BeanUtil.copyProperties(dto, record);
        record.setUserId(userId);
        record.setRecordDate(LocalDate.now());
        record.setCreatedAt(LocalDateTime.now());

        save(record);
        return record.getId();
    }

    @Override
    public List<SleepRecordVo> getRecords(Long userId, RecordSelectDto dto) {
        // 支持按日期范围查询
        List<SleepRecord> records = lambdaQuery()
                .eq(SleepRecord::getUserId, userId)
                .orderByDesc(SleepRecord::getRecordDate)
                .ge(dto.getStartDate() != null, SleepRecord::getRecordDate, dto.getStartDate())
                .le(dto.getEndDate() != null, SleepRecord::getRecordDate, dto.getEndDate())
                .list();

        if (records.isEmpty()) {
            return List.of();
        }

        // 转换为VO对象
        return records.stream()
                .map(record -> {
                    SleepRecordVo vo = new SleepRecordVo();
                    BeanUtil.copyProperties(record, vo);
                    return vo;
                })
                .toList();
    }
}