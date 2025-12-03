package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseType;

import java.util.List;

/**
 * 运动类型Service接口
 */
public interface ExerciseTypeService extends IService<ExerciseType> {

    /**
     * 查询所有运动类型
     *
     * @return 运动类型列表
     */
    List<ExerciseType> getAllTypes();

    /**
     * 新增运动类型
     *
     * @param typeName 运动类型名称
     * @return 记录ID
     */
    Long addType(String typeName);
}