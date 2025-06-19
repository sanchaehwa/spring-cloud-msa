package com.example.catalog_service.exception;

import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.global.exception.BusinessException;

public class ExistCategoryException extends BusinessException {
    public ExistCategoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
