package com.example.catalog_service.global.exception;
import com.example.catalog_service.global.ErrorCode;
import lombok.Getter;

@Getter
public abstract class DuplicateException extends BusinessException {
    private final ErrorCode errorCode;
    protected DuplicateException(ErrorCode errorCode){
        super(errorCode);
        this.errorCode = errorCode;
    }
}
