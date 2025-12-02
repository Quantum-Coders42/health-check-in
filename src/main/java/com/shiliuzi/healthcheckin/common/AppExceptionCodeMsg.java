package com.shiliuzi.healthcheckin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppExceptionCodeMsg {
    RECORD_EXIST("10001", "该日期已有运动打卡记录"),
    DIET_RECORD_EXIST("10002", "该日期该餐食类型已有打卡记录"),
    SLEEP_RECORD_EXIST("10003", "该日期已有睡眠打卡记录"),
    INVALID_SLEEP_TIME("10004", "起床时间必须在入睡时间之后"),
    INVALID_EXERCISE_TYPE("10005", "无效的运动类型ID"),
    TOKEN_TEST_ERROR("40100", "验证token失败");

    private String code;
    private String msg;
}
