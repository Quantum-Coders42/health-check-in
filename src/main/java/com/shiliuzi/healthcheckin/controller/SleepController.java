package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.dto.SleepCheckInDto;
import com.shiliuzi.healthcheckin.pojo.vo.SleepRecordVo;
import com.shiliuzi.healthcheckin.service.SleepRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 睡眠打卡控制器
 */
@RestController
@RequestMapping("/sleep")
public class SleepController {

    private final SleepRecordService sleepRecordService;

    @Autowired
    public SleepController(SleepRecordService sleepRecordService) {
        this.sleepRecordService = sleepRecordService;
    }

    /**
     * 新增睡眠打卡记录
     *
     * @param dto 打卡信息
     * @return 操作结果
     */
    @PostMapping("/checkin")
    public Result<Long> addSleepRecord(@Valid @RequestBody SleepCheckInDto dto, HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(sleepRecordService.addRecord(dto, userId));
    }

    /**
     * 获取睡眠记录列表
     * @return 记录列表
     */
    @GetMapping("/records")
    public Result<List<SleepRecordVo>> getSleepRecords(@RequestBody(required = false) RecordSelectDto dto,
                                                        HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(sleepRecordService.getRecords(userId, dto));
    }
}