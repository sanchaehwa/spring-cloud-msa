package com.example.catalog_service.exception;

import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.global.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
