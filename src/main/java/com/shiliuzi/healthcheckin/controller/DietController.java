package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.pojo.dto.CheckInRecordDto;
import com.shiliuzi.healthcheckin.pojo.dto.RecordSelectDto;
import com.shiliuzi.healthcheckin.pojo.po.DietRecord;
import com.shiliuzi.healthcheckin.service.DietRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 饮食打卡控制器
 */
@RestController
@RequestMapping("/diet")
public class DietController {

    private final DietRecordService dietRecordService;

    @Autowired
    public DietController(DietRecordService dietRecordService) {
        this.dietRecordService = dietRecordService;
    }

    /**
     * 新增饮食打卡记录
     * @param dto 打卡信息
     * @return 操作结果
     */
    @PostMapping("/checkin")
    public Result<Long> addDietRecord(@Valid @RequestBody(required = false) CheckInRecordDto dto, HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(dietRecordService.addRecord(dto, userId));
    }

    /**
     * 获取饮食记录列表
     * @return 记录列表
     */
    @GetMapping("/records")
    public Result<List<DietRecord>> getDietRecords(@Valid @RequestBody(required = false) RecordSelectDto dto,
                                                           HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        List<DietRecord> records = dietRecordService.getRecords(userId, dto);
        return Result.success(records);
    }
}