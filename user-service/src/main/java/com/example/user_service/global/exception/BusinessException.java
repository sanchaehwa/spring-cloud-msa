package com.example.user_service.global.exception;

import com.example.user_service.global.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
   private final ErrorCode errorCode;

   public ErrorCode getErrorCode() {
       return errorCode;
   }

}
