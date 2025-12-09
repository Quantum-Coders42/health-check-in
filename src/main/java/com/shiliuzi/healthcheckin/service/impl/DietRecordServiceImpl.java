package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import com.shiliuzi.healthcheckin.common.exception.ServiceException;
import com.shiliuzi.healthcheckin.mapper.DietRecordMapper;
import com.shiliuzi.healthcheckin.pojo.dto.DietCheckInDto;
import com.shiliuzi.healthcheckin.pojo.po.DietRecord;
import com.shiliuzi.healthcheckin.pojo.po.MealType;
import com.shiliuzi.healthcheckin.pojo.vo.DietRecordVo;
import com.shiliuzi.healthcheckin.service.DietRecordService;
import com.shiliuzi.healthcheckin.service.FoodService;
import com.shiliuzi.healthcheckin.service.MealTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 饮食打卡记录Service实现类
 */
@Service
public class DietRecordServiceImpl extends ServiceImpl<DietRecordMapper, DietRecord>
        implements DietRecordService {

    @Autowired
    private MealTypeService mealTypeService;

    @Autowired
    private FoodService foodService;

    @Override
    @Transactional
    public Long addRecord(DietCheckInDto dto, Long userId) {
        // 检查同一天同餐食类型是否已存在打卡记录
        List<DietRecord> records = lambdaQuery()
                .eq(DietRecord::getUserId, userId)
                .eq(DietRecord::getMealTypeId, dto.getMealTypeId())
                .eq(DietRecord::getRecordDate, LocalDate.now())
                .list();

        if (!records.isEmpty()) {
            throw new ServiceException(AppExceptionCodeMsg.DIET_RECORD_EXIST);
        }

        // 验证用餐类型是否存在
        MealType mealType = mealTypeService.getById(dto.getMealTypeId());
        if (mealType == null) {
            throw new ServiceException(AppExceptionCodeMsg.INVALID_MEAL_TYPE);
        }

        // 构建实体对象
        DietRecord record = new DietRecord();
        record.setUserId(userId);
        BeanUtil.copyProperties(dto, record);
        record.setRecordDate(LocalDate.now());
        record.setCreatedAt(LocalDateTime.now());

        save(record);

        // 保存食物信息
        if (dto.getFoods() != null && !dto.getFoods().isEmpty()) {
            foodService.saveFoods(dto.getFoods(), record.getId());
        }

        return record.getId();
    }

    @Override
    public List<DietRecordVo> getRecords(Long userId, LocalDate startDate, LocalDate endDate) {
        // 1. 查询饮食记录
        List<DietRecord> records = lambdaQuery()
                .eq(DietRecord::getUserId, userId)
                .orderByDesc(DietRecord::getRecordDate)
                .ge(startDate != null, DietRecord::getRecordDate, startDate)
                .le(endDate != null, DietRecord::getRecordDate, endDate)
                .list();

        if (records.isEmpty()) {
            return List.of();
        }

        // 2. 提取所有用餐类型ID
        Set<Long> mealTypeIds = records.stream()
                .map(DietRecord::getMealTypeId)
                .collect(Collectors.toSet());

        // 3. 批量查询用餐类型
        List<MealType> mealTypes = mealTypeService.listByIds(mealTypeIds);

        // 4. 构建ID到名称的映射
        Map<Long, String> mealTypeNameMap = mealTypes.stream()
                .collect(Collectors.toMap(MealType::getId, MealType::getTypeName));

        // 5. 转换为VO对象并查询食物信息
        return records.stream()
                .map(record -> {
                    DietRecordVo vo = new DietRecordVo();
                    BeanUtil.copyProperties(record, vo);
                    vo.setMealTypeName(mealTypeNameMap.get(record.getMealTypeId()));

                    // 查询食物信息
                    vo.setFoods(foodService.getFoodsByDietRecordId(record.getId()));
                    return vo;
                })
                .toList();
    }
}