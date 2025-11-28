package com.shiliuzi.healthcheckin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppExceptionCodeMsg {
    RECORD_EXIST("10001", "该日期已有运动打卡记录"),
    TOKEN_TEST_ERROR("40100", "验证token失败");


    private String code;
    private String msg;
}
