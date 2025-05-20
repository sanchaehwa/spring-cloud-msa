package com.example.user_service.global.exception;

import com.example.user_service.global.ErrorCode;

public class DuplicateUserException extends BusinessException {
    public DuplicateUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
