package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.po.FoodType;

import java.util.List;

/**
 * 食物类型Service接口
 */
public interface FoodTypeService extends IService<FoodType> {

    /**
     * 查询所有食物类型
     *
     * @return 食物类型列表
     */
    List<FoodType> getAllTypes();

    /**
     * 新增食物类型
     *
     * @param typeName 食物类型名称
     * @return 是否成功
     */
    boolean addType(String typeName);
}