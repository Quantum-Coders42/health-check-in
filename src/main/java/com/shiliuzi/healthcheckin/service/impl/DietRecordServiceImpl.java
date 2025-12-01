package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.DietRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.DietCheckInDto;
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
    public Long addDietRecord(DietCheckInDto dto, Long userId) {
        // 检查是否已存在同一天同一类型餐食的打卡记录
        List<DietRecord> records = lambdaQuery()
                .eq(DietRecord::getUserId, userId)
                .eq(DietRecord::getRecordDate, LocalDate.now())
                .eq(DietRecord::getMealType, dto.getMealType())
                .list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.DIET_RECORD_EXIST);
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
    public List<DietRecord> getDietRecords(Long userId, LocalDate startDate, LocalDate endDate, String mealType) {
        LambdaQueryChainWrapper<DietRecord> queryWrapper = lambdaQuery()
                .eq(DietRecord::getUserId, userId)
                .orderByDesc(DietRecord::getRecordDate)
                .ge(startDate != null, DietRecord::getRecordDate, startDate)
                .le(endDate != null, DietRecord::getRecordDate, endDate);

        if (mealType != null && !mealType.trim().isEmpty()) {
            queryWrapper.eq(DietRecord::getMealType, mealType);
        }

        return list(queryWrapper);
    }
}