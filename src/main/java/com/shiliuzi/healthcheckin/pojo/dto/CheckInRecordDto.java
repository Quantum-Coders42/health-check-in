package com.shiliuzi.healthcheckin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 通用打卡记录DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRecordDto {

    /**
     * 描述信息
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;
}