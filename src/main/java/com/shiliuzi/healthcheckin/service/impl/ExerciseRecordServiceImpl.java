package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.ExerciseRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.CheckInRecordDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseRecord;
import com.shiliuzi.healthcheckin.service.ExerciseRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 运动打卡记录Service实现类
 */
@Service
public class ExerciseRecordServiceImpl extends ServiceImpl<ExerciseRecordMapper, ExerciseRecord>
        implements ExerciseRecordService {

    @Override
    public Long addRecord(CheckInRecordDto dto,Long userId) {
        // 检查是否已存在同一天的打卡记录
        List<ExerciseRecord> records =lambdaQuery().eq(ExerciseRecord::getUserId, userId)
                   .eq(ExerciseRecord::getRecordDate, LocalDate.now()).list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.RECORD_EXIST);
        }

        // 构建实体对象
        ExerciseRecord record = new ExerciseRecord();
        BeanUtil.copyProperties(dto, record);
        record.setUserId(userId);
        record.setRecordDate(LocalDate.now());
        record.setCreatedAt(LocalDateTime.now());

        save(record);
        return record.getId();
    }

    @Override
    public List<ExerciseRecord> getRecords(Long userId, RecordSelectDto dto) {
        if (dto == null) {
            dto = new RecordSelectDto();
        }
        return lambdaQuery().eq(ExerciseRecord::getUserId, userId)
                .orderByDesc(ExerciseRecord::getRecordDate)
                .ge(dto.getStartDate() != null,ExerciseRecord::getRecordDate, dto.getStartDate())
                .le(dto.getEndDate() != null,ExerciseRecord::getRecordDate, dto.getEndDate())
                .list();
    }
}