package com.example.user_service.global.exception;

import com.example.user_service.global.ErrorCode;

public class NotFoundException extends BusinessException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
