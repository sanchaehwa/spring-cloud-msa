package com.example.catalog_service.exception;

import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.global.exception.DuplicateException;

public class DuplicateProductException extends DuplicateException {
    public DuplicateProductException(ErrorCode errorCode) {
        super(errorCode);
    }
}
