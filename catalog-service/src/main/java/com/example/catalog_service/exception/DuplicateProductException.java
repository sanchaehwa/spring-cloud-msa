package com.example.catalog_service.exception;

import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.global.exception.BusinessException;

public class DuplicateProductException extends BusinessException {
    public DuplicateProductException(ErrorCode errorCode) {
        super(errorCode);
    }
}
