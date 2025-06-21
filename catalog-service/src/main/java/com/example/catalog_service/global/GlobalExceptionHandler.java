package com.example.catalog_service.global;

import com.example.catalog_service.exception.*;
import com.example.catalog_service.global.exception.ApiException;
import com.example.catalog_service.global.exception.DuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Unexpected Exception: {}", exception.getMessage(), exception);
        return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception) {
        log.error("ApiException: {}", exception.getMessage(), exception);
        return buildErrorResponse(exception.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Validation Failed: {}", exception.getMessage(), exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT, exception.getBindingResult());
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus()).body(errorResponse);
    }

    @ExceptionHandler({DuplicateCatalogName.class, DuplicateProductException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException exception) {
        log.error("DuplicateException: {}", exception.getMessage(), exception);
        ErrorCode errorCode = (exception instanceof DuplicateCatalogName)
                ? ErrorCode.DUPLICATE_CATALOG_EXCEPTION
                : ErrorCode.DUPLICATE_PRODUCT_EXCEPTION;
        return buildErrorResponse(errorCode);
    }

    @ExceptionHandler(ExistCategoryException.class)
    public ResponseEntity<ErrorResponse> handleCategoryException(ExistCategoryException exception) {
        log.error("ExistCategoryException: {}", exception.getMessage(), exception);
        return buildErrorResponse(ErrorCode.EXIST_CATALOG_EXCEPTION);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exception) {
        log.error("ProductNotFoundException: {}", exception.getMessage(), exception);
        return buildErrorResponse(ErrorCode.NOT_FOUND_PRODUCT_EXCEPTION);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }
}
