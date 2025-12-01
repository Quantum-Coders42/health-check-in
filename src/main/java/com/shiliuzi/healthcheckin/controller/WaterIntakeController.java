package com.shiliuzi.healthcheckin.controller;

import com.shiliuzi.healthcheckin.common.Result;
import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.pojo.dto.WaterIntakeCheckInDto;
import com.shiliuzi.healthcheckin.pojo.po.WaterIntakeRecord;
import com.shiliuzi.healthcheckin.service.WaterIntakeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return Result.success(waterIntakeService.addWaterIntakeRecord(dto, userId));
    }

    /**
     * 获取饮水记录列表
     *
     * @param startDate 开始日期 (可选)
     * @param endDate 结束日期 (可选)
     * @return 记录列表
     */
    @GetMapping("/records")
    public Result<List<WaterIntakeRecord>> getWaterIntakeRecords(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        List<WaterIntakeRecord> records = waterIntakeService.getWaterIntakeRecords(userId, startDate, endDate);
        return Result.success(records);
    }

    /**
     * 获取每日饮水量统计
     *
     * @param date 查询日期 (可选，默认今天)
     * @return 饮水量统计
     */
    @GetMapping("/daily-summary")
    public Result<Map<String, Object>> getDailyWaterSummary(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        LocalDate queryDate = date != null ? date : LocalDate.now();

        Integer totalIntake = waterIntakeService.getDailyWaterIntake(userId, queryDate);

        Map<String, Object> summary = new HashMap<>();
        summary.put("date", queryDate);
        summary.put("totalIntakeMl", totalIntake);
        summary.put("intakeCount", waterIntakeService.getWaterIntakeRecords(userId, queryDate, queryDate).size());

        return Result.success(summary);
    }

    /**
     * 快捷打卡 - 200ml
     */
    @PostMapping("/quick-checkin-200ml")
    public Result<Long> quickCheckIn200ml(@RequestBody(required = false) Map<String, String> description, HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        WaterIntakeCheckInDto dto = new WaterIntakeCheckInDto();
        dto.setAmountMl(200);
        if (description != null && description.containsKey("description")) {
            dto.setDescription(description.get("description"));
        }
        return Result.success(waterIntakeService.addWaterIntakeRecord(dto, userId));
    }

    /**
     * 快捷打卡 - 500ml
     */
    @PostMapping("/quick-checkin-500ml")
    public Result<Long> quickCheckIn500ml(@RequestBody(required = false) Map<String, String> description, HttpServletRequest request) {
        Long userId = JwtInterceptor.getUserIdFromReq(request);
        WaterIntakeCheckInDto dto = new WaterIntakeCheckInDto();
        dto.setAmountMl(500);
        if (description != null && description.containsKey("description")) {
            dto.setDescription(description.get("description"));
        }
        return Result.success(waterIntakeService.addWaterIntakeRecord(dto, userId));
    }
}