package com.example.user_service.jwt.exception;

import com.example.user_service.global.ErrorCode;
import com.example.user_service.global.exception.BusinessException;

public class InvalidTokenException extends BusinessException {
  public InvalidTokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
