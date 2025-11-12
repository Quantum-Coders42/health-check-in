package com.shiliuzi.healthcheckin.common.exception;

import com.shiliuzi.healthcheckin.common.AppExceptionCodeMsg;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    public final String code;

    public ServiceException(String msg) {
        super(msg);
        this.code = "500";
    }

    public ServiceException(Exception e) {
        super(e);
        this.code = "500";
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(AppExceptionCodeMsg appExceptionCodeMsg) {
        super(appExceptionCodeMsg.getMsg());
        this.code = appExceptionCodeMsg.getCode();
    }
}
