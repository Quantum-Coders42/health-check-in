package com.shiliuzi.healthcheckin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.mapper.FoodMapper;
import com.shiliuzi.healthcheckin.pojo.dto.FoodDto;
import com.shiliuzi.healthcheckin.pojo.po.Food;
import com.shiliuzi.healthcheckin.pojo.po.FoodType;
import com.shiliuzi.healthcheckin.pojo.vo.FoodVo;
import com.shiliuzi.healthcheckin.service.FoodService;
import com.shiliuzi.healthcheckin.service.FoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 食物Service实现类
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food>
        implements FoodService {

    @Autowired
    private FoodTypeService foodTypeService;

    @Override
    public List<FoodVo> saveFoods(List<FoodDto> foodDtos, Long dietRecordId) {
        if (foodDtos == null || foodDtos.isEmpty()) {
            return List.of();
        }

        List<Food> foods = foodDtos.stream().map(dto -> {
            Food food = new Food();
            BeanUtil.copyProperties(dto, food);
            food.setDietRecordId(dietRecordId);
            food.setCreatedAt(LocalDateTime.now());
            return food;
        }).collect(Collectors.toList());

        saveBatch(foods);

        // 转换为VO
        return convertToVo(foods);
    }

    @Override
    public List<FoodVo> getFoodsByDietRecordId(Long dietRecordId) {
        List<Food> foods = lambdaQuery()
                .eq(Food::getDietRecordId, dietRecordId)
                .orderByAsc(Food::getId)
                .list();

        return convertToVo(foods);
    }

    private List<FoodVo> convertToVo(List<Food> foods) {
        if (foods.isEmpty()) {
            return List.of();
        }

        // 提取所有食物类型ID
        Set<Long> typeIds = foods.stream()
                .map(Food::getFoodTypeId)
                .collect(Collectors.toSet());

        // 批量查询食物类型
        List<FoodType> foodTypes = foodTypeService.listByIds(typeIds);

        // 构建ID到名称的映射
        Map<Long, String> typeNameMap = foodTypes.stream()
                .collect(Collectors.toMap(FoodType::getId, FoodType::getTypeName));

        // 转换为VO对象
        return foods.stream()
                .map(food -> {
                    FoodVo vo = new FoodVo();
                    BeanUtil.copyProperties(food, vo);
                    vo.setFoodTypeName(typeNameMap.get(food.getFoodTypeId()));
                    return vo;
                })
                .collect(Collectors.toList());
    }
}