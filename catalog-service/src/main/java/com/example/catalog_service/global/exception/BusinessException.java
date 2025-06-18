package com.example.catalog_service.global.exception;

import com.example.catalog_service.global.ErrorCode;

public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode() {return errorCode;}
}

