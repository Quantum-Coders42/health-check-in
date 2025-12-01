package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.pojo.dto.SleepCheckInDto;
import com.shiliuzi.healthcheckin.pojo.po.SleepRecord;
import com.shiliuzi.healthcheckin.service.SleepRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return Result.success(sleepRecordService.addSleepRecord(dto, userId));
    }

    /**
     * 获取睡眠记录列表
     *
     * @param startDate 开始日期 (可选)
     * @param endDate 结束日期 (可选)
     * @return 记录列表
     */
    @GetMapping("/records")
    public Result<List<SleepRecord>> getSleepRecords(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        List<SleepRecord> records = sleepRecordService.getSleepRecords(userId, startDate, endDate);
        return Result.success(records);
    }
}