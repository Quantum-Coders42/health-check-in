package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.DietRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.CheckInRecordDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.DietRecord;
import com.shiliuzi.healthcheckin.service.DietRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 饮食打卡记录Service实现类
 */
@Service
public class DietRecordServiceImpl extends ServiceImpl<DietRecordMapper, DietRecord>
        implements DietRecordService {

    @Override
    public Long addRecord(CheckInRecordDto dto, Long userId) {
        // 检查同一天是否已存在打卡记录
        List<DietRecord> records = lambdaQuery()
                .eq(DietRecord::getUserId, userId)
                .eq(DietRecord::getRecordDate, LocalDate.now())
                .list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.RECORD_EXIST);
        }

        // 构建实体对象
        DietRecord record = new DietRecord();
        BeanUtil.copyProperties(dto, record);
        record.setUserId(userId);
        record.setRecordDate(LocalDate.now());
        record.setCreatedAt(LocalDateTime.now());

        save(record);
        return record.getId();
    }

    @Override
    public List<DietRecord> getRecords(Long userId, RecordSelectDto dto) {
        // 支持按日期范围查询
        return lambdaQuery()
                .eq(DietRecord::getUserId, userId)
                .orderByDesc(DietRecord::getRecordDate)
                .ge(dto.getStartDate() != null, DietRecord::getRecordDate, dto.getStartDate())
                .le(dto.getEndDate() != null, DietRecord::getRecordDate, dto.getEndDate())
                .list();
    }
}