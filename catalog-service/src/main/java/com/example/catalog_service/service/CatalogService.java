package com.example.catalog_service.service;

import com.example.catalog_service.domain.Catalog;
import com.example.catalog_service.dto.CatalogSaveRequest;
import com.example.catalog_service.exception.DuplicateCatalogName;
import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    @Transactional
    public Long saveCatalog(CatalogSaveRequest catalogSaveRequest) {
        validateCatalog(catalogSaveRequest);
        Catalog catalog = catalogSaveRequest.toEntity();
        return catalogRepository.save(catalog).getId();

    }


    //이미 등록되어있는 카테고리인지 확인
    public void validateCatalog(CatalogSaveRequest catalogSaveRequest) {
        if(catalogRepository.existsByCategoryNameAndIsDeletedFalse(catalogSaveRequest.getCategoryName())){
            throw new DuplicateCatalogName(ErrorCode.DUPLICATE_CATALOG_EXCEPTION);
        }
    }
}
