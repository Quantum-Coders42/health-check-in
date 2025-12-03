package com.shiliuzi.healthcheckin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.healthcheckin.mapper.ExerciseTypeMapper;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseType;
import com.shiliuzi.healthcheckin.service.ExerciseTypeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运动类型Service实现类
 */
@Service
public class ExerciseTypeServiceImpl extends ServiceImpl<ExerciseTypeMapper, ExerciseType>
        implements ExerciseTypeService {

    @Override
    public List<ExerciseType> getAllTypes() {
        return lambdaQuery().orderByAsc(ExerciseType::getTypeName).list();
    }

    @Override
    public Long addType(String typeName) {
        // 检查类型名称是否已存在
        ExerciseType existType = lambdaQuery()
                .eq(ExerciseType::getTypeName, typeName)
                .one();

        if (existType != null) {
            return null;
        }

        ExerciseType exerciseType = new ExerciseType();
        exerciseType.setTypeName(typeName);

        save(exerciseType);
        return exerciseType.getId();
    }
}