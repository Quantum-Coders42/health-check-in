package com.shiliuzi.healthcheckin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.healthcheckin.pojo.dto.ExerciseCheckInDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.ExerciseRecord;
import com.shiliuzi.healthcheckin.pojo.vo.ExerciseRecordVo;

import java.time.LocalDate;
import java.util.List;

/**
 * 运动打卡记录Service接口
 */
public interface ExerciseRecordService extends IService<ExerciseRecord> {

    /**
     * 新增运动打卡记录
     *
     * @param dto 打卡信息
     * @return 是否成功
     */
    Long addRecord(ExerciseCheckInDto dto,Long userId);

    List<ExerciseRecordVo> getRecords(Long userId, RecordSelectDto dto);
}