package com.example.catalog_service.global.exception;
import com.example.catalog_service.global.ErrorCode;

public class DuplicateException extends BusinessException {
    public DuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
