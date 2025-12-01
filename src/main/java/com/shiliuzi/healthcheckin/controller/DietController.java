package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.pojo.dto.DietCheckInDto;
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
     *
     * @param dto 打卡信息
     * @return 操作结果
     */
    @PostMapping("/checkin")
    public Result<Long> addDietRecord(@Valid @RequestBody DietCheckInDto dto, HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(dietRecordService.addDietRecord(dto, userId));
    }

    /**
     * 获取饮食记录列表
     *
     * @param startDate 开始日期 (可选)
     * @param endDate 结束日期 (可选)
     * @param mealType 饮食类型 (可选)
     * @return 记录列表
     */
    @GetMapping("/records")
    public Result<List<DietRecord>> getDietRecords(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) String mealType,
            HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        List<DietRecord> records = dietRecordService.getDietRecords(userId, startDate, endDate, mealType);
        return Result.success(records);
    }
}