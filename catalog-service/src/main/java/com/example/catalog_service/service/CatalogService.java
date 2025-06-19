package com.example.catalog_service.service;

import com.example.catalog_service.domain.Catalog;
import com.example.catalog_service.dto.CatalogDto;
import com.example.catalog_service.dto.CatalogSaveRequest;
import com.example.catalog_service.exception.DuplicateCatalogName;
import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    //카테고리 등록은 어드민이나 일반 사용자는 못함 -> 인증 객체가 필요한데 : Userservice에서 그 인증된 사용자를 어떻게 가지고 올수 있을지..
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
    //삭제되지 않은 모든 카테고리 이름 조회
    @Transactional(readOnly = true)
    public List<String> getAllCatalogNames() {
        return catalogRepository.findAllByIsDeletedFalse();
    }
    @Transactional(readOnly = true)
    public List<CatalogDto> getCatalogDetail(Long id) {
        return catalogRepository.findAllByIdAndIsDeletedFalse(id);
    }

}
