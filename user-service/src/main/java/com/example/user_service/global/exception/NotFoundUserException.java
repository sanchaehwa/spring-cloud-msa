package com.example.user_service.global.exception;

import com.example.user_service.global.ErrorCode;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
