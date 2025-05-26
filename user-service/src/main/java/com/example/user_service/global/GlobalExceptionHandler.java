package com.example.user_service.global;

import com.example.user_service.exception.DuplicateUserException;
import com.example.user_service.exception.NotFoundUserException;
import com.example.user_service.global.exception.ApiException;
import com.example.user_service.global.exception.NotFoundException;
import com.example.user_service.jwt.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //
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

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException exception) {
        log.error("handleNotFoundException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_FOUND_RESOURCE);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException exception) {
        log.error("handleDuplicateUserException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.CONFLICT_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException exception) {
        log.error("handleInvalidTokenException", exception);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TOKEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
