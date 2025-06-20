package com.example.catalog_service.global;

import com.example.catalog_service.exception.DuplicateCatalogName;
import com.example.catalog_service.exception.DuplicateProductException;
import com.example.catalog_service.exception.ExistCategoryException;
import com.example.catalog_service.exception.ProductNotFoundException;
import com.example.catalog_service.global.exception.ApiException;
import com.example.catalog_service.global.exception.DuplicateException;
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
    @ExceptionHandler({DuplicateCatalogName.class, DuplicateProductException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException exception) {
        log.error("handleDuplicateException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.CONFLICT_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ExistCategoryException.class)
    public ResponseEntity<ErrorResponse> handleCategoryException(ExistCategoryException exception) {
        log.error("handleCategoryException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.EXIST_CATALOG_EXCEPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exception) {
        log.error("handleProductNotFoundException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_FOUND_PRODUCT_EXCEPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
