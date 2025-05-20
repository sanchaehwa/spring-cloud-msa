package com.example.user_service.global;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("정의되지 않은 에러가 발생했습니다.", 500),
    INVALID_INPUT("올바른 입력 형식이 아닙니다.", 400),
    NOT_FOUND_RESOURCE("존재하지 않는 리소스입니다.", 404),
    CONFLICT_ERROR("중복된 값입니다.", 409),

    DUPLICATE_USER("이미 가입되어 있는 사용자 입니다", 409),
    NOT_FOUND_USER("사용자를 찾을 수 없습니다", 404);

    private final String message;
    private final int status;
}
