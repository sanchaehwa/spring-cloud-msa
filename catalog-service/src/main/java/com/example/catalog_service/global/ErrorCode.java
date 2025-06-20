package com.example.catalog_service.global;

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

    //카테고리
    DUPLICATE_CATALOG_EXCEPTION("이미 등록된 카테고리입니다",409),
    EXIST_CATALOG_EXCEPTION("등록되어 있지 않은 카테고리입니다.",404),
    //상품
    DUPLICATE_PRODUCT_EXCEPTION("이미 등록된 상품입니다",409),
    NOT_FOUND_PRODUCT_EXCEPTION("존재 하지않는 상품입니다",404);


    private final String message;
    private final int status;
}
