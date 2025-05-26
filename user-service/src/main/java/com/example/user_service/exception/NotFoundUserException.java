package com.example.user_service.exception;

import com.example.user_service.global.ErrorCode;
import com.example.user_service.global.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
