package com.shiliuzi.healthcheckin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.mapper.FoodTypeMapper;
import com.shiliuzi.healthcheckin.pojo.po.FoodType;
import com.shiliuzi.healthcheckin.service.FoodTypeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 食物类型Service实现类
 */
@Service
public class FoodTypeServiceImpl extends ServiceImpl<FoodTypeMapper, FoodType>
        implements FoodTypeService {

    @Override
    public List<FoodType> getAllTypes() {
        return lambdaQuery().orderByAsc(FoodType::getTypeName).list();
    }

    @Override
    public boolean addType(String typeName) {
        // 检查类型名称是否已存在
        FoodType existType = lambdaQuery()
                .eq(FoodType::getTypeName, typeName)
                .one();

        if (existType != null) {
            return false;
        }

        FoodType foodType = new FoodType();
        foodType.setTypeName(typeName);

        return save(foodType);
    }
}