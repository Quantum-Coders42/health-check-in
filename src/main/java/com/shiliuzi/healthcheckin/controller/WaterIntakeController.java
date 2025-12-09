package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.pojo.dto.WaterIntakeCheckInDto;
import com.shiliuzi.healthcheckin.pojo.vo.WaterIntakeRecordVo;
import com.shiliuzi.healthcheckin.service.WaterIntakeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 饮水记录控制器
 */
@RestController
@RequestMapping("/water")
public class WaterIntakeController {

    private final WaterIntakeService waterIntakeService;

    @Autowired
    public WaterIntakeController(WaterIntakeService waterIntakeService) {
        this.waterIntakeService = waterIntakeService;
    }

    /**
     * 新增饮水记录
     *
     * @param dto 打卡信息
     * @return 操作结果
     */
    @PostMapping("/checkin")
    public Result<Long> addWaterIntakeRecord(@Valid @RequestBody WaterIntakeCheckInDto dto, HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(waterIntakeService.addRecord(dto, userId));
    }

    /**
     * 获取饮水记录列表
     * @return 记录列表
     */
    @GetMapping("/records")
    public Result<List<WaterIntakeRecordVo>> getWaterIntakeRecords(@RequestParam(required = false) LocalDate startDate,
                                                                   @RequestParam(required = false) LocalDate endDate,
                                                                   HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        return Result.success(waterIntakeService.getRecords(userId, startDate, endDate));
    }
}