package com.example.catalog_service.exception;

import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.global.exception.BusinessException;

public class DuplicateCatalogName extends BusinessException {
public DuplicateCatalogName(ErrorCode errorCode) {
    super(errorCode);
}
}
