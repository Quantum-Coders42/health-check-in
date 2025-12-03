package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.po.MealType;

import java.util.List;

/**
 * 用餐类型Service接口
 */
public interface MealTypeService extends IService<MealType> {

    /**
     * 查询所有用餐类型
     *
     * @return 用餐类型列表
     */
    List<MealType> getAllTypes();

    /**
     * 新增用餐类型
     *
     * @param typeName 用餐类型名称
     * @return 是否成功
     */
    boolean addType(String typeName);
}