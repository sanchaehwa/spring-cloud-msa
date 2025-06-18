package com.example.catalog_service.dto;

import com.example.catalog_service.domain.Catalog;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatalogSaveRequest {

    @NotBlank(message = "카테고리명을 작성해주세요")
    private String categoryName;

    public Catalog toEntity() {
        return Catalog.builder()
                .categoryName(categoryName)
                .build();
    }
}
