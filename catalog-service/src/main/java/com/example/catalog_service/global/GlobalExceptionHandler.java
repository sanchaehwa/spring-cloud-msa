package com.example.catalog_service.global;

import com.example.catalog_service.exception.DuplicateCatalogName;
import com.example.catalog_service.global.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("handelException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ) {
        log.error("handleMethodArgumentNotValidException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(
                ErrorCode.INVALID_INPUT, exception.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateCatalogName.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateCatalogName exception) {
        log.error("handleDuplicateCatalogNameException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.DUPLICATE_CATALOG_EXCEPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
