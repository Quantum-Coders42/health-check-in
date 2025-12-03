package com.shiliuzi.healthcheckin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.mapper.MealTypeMapper;
import com.shiliuzi.healthcheckin.pojo.po.MealType;
import com.shiliuzi.healthcheckin.service.MealTypeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用餐类型Service实现类
 */
@Service
public class MealTypeServiceImpl extends ServiceImpl<MealTypeMapper, MealType>
        implements MealTypeService {

    @Override
    public List<MealType> getAllTypes() {
        return lambdaQuery().orderByAsc(MealType::getTypeName).list();
    }

    @Override
    public Long addType(String typeName) {
        // 检查类型名称是否已存在
        MealType existType = lambdaQuery()
                .eq(MealType::getTypeName, typeName)
                .one();

        if (existType != null) {
            return null;
        }

        MealType mealType = new MealType();
        mealType.setTypeName(typeName);

        save(mealType);
        return mealType.getId();
    }
}