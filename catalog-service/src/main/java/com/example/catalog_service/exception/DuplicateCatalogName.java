package com.example.catalog_service.exception;

import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.global.exception.DuplicateException;

public class DuplicateCatalogName extends DuplicateException {
public DuplicateCatalogName(ErrorCode errorCode) {
    super(errorCode);
}
}
