package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.ExerciseRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.ExerciseCheckInDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseRecord;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseType;
import com.shiliuzi.healthcheckin.pojo.vo.ExerciseRecordVo;
import com.shiliuzi.healthcheckin.service.ExerciseRecordService;
import com.shiliuzi.healthcheckin.service.ExerciseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 运动打卡记录Service实现类
 */
@Service
public class ExerciseRecordServiceImpl extends ServiceImpl<ExerciseRecordMapper, ExerciseRecord>
        implements ExerciseRecordService {

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Override
    public Long addRecord(ExerciseCheckInDto dto, Long userId) {
        // 检查是否已存在同一天的打卡记录
        List<ExerciseRecord> records = lambdaQuery().eq(ExerciseRecord::getUserId, userId)
                   .eq(ExerciseRecord::getRecordDate, LocalDate.now()).list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.RECORD_EXIST);
        }

        // 验证运动类型是否存在
        ExerciseType exerciseType = exerciseTypeService.getById(dto.getExerciseTypeId());
        if (exerciseType == null) {
            throw new ServiceException(AppExceptionCodeMsg.INVALID_EXERCISE_TYPE);
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
    public List<ExerciseRecordVo> getRecords(Long userId, RecordSelectDto dto) {
        if (dto == null) {
            dto = new RecordSelectDto();
        }

        // 1. 查询运动记录
        List<ExerciseRecord> records = lambdaQuery().eq(ExerciseRecord::getUserId, userId)
                .orderByDesc(ExerciseRecord::getRecordDate)
                .ge(dto.getStartDate() != null,ExerciseRecord::getRecordDate, dto.getStartDate())
                .le(dto.getEndDate() != null,ExerciseRecord::getRecordDate, dto.getEndDate())
                .list();

        if (records.isEmpty()) {
            return List.of();
        }

        // 2. 提取所有运动类型ID
        Set<Long> typeIds = records.stream()
                .map(ExerciseRecord::getExerciseTypeId)
                .collect(Collectors.toSet());

        // 3. 批量查询运动类型
        List<ExerciseType> types = exerciseTypeService.listByIds(typeIds);

        // 4. 构建ID到名称的映射
        Map<Long, String> typeNameMap = types.stream()
                .collect(Collectors.toMap(ExerciseType::getId, ExerciseType::getTypeName));

        // 5. 转换为VO对象
        return records.stream()
                .map(record -> {
                    ExerciseRecordVo vo = new ExerciseRecordVo();
                    BeanUtil.copyProperties(record, vo);
                    vo.setExerciseTypeName(typeNameMap.get(record.getExerciseTypeId()));
                    return vo;
                })
                .collect(Collectors.toList());
    }
}