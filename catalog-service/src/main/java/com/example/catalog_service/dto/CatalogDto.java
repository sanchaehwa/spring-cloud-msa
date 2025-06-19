package com.example.catalog_service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatalogDto {
    private Long id;
    private String categoryName;
    private String productName;
    private Integer price;
    private boolean isDeleted;

    @Builder
    public CatalogDto(Long id, String categoryName, String productName, Integer price, boolean isDeleted) {
        this.id = id;
        this.categoryName = categoryName;
        this.productName = productName;
        this.price = price;
        this.isDeleted = isDeleted;
    }
}
