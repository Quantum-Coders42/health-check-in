package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.pojo.dto.ExerciseCheckInDto;
import com.shiliuzi.healthcheckin.pojo.vo.ExerciseRecordVo;
import com.shiliuzi.healthcheckin.service.ExerciseRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 运动打卡控制器
 */
@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseRecordService exerciseRecordService;

    @Autowired
    public ExerciseController(ExerciseRecordService exerciseRecordService) {
        this.exerciseRecordService = exerciseRecordService;
    }

    /**
     * 新增运动打卡记录
     *
     * @param dto 打卡信息
     * @return 操作结果
     */
    @PostMapping("/checkin")
    public Result<Long> addExerciseRecord(@Valid @RequestBody ExerciseCheckInDto dto, HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(exerciseRecordService.addRecord(dto,userId));
    }

    @GetMapping("/records")
    public Result<List<ExerciseRecordVo>> getExerciseRecords(@RequestParam(required = false) LocalDate startDate,
                                                           @RequestParam(required = false) LocalDate endDate,
                                                           HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(exerciseRecordService.getRecords(userId, startDate, endDate));
    }
}