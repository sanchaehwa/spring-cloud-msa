package com.example.user_service.exception;

import com.example.user_service.global.ErrorCode;
import com.example.user_service.global.exception.BusinessException;

public class DuplicateUserException extends BusinessException {
    public DuplicateUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
