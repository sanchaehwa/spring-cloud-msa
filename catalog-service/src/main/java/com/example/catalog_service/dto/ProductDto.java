package com.example.catalog_service.dto;

import com.example.catalog_service.domain.Catalog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDto {
    private Long id;
    private String categoryName;
    private String productName;
    private Integer stock;
    private Integer price;
    private boolean isDeleted;

    @Builder
    public ProductDto(Long id, String productName, String categoryName ,Integer stock, Integer price, Boolean isDeleted) {
        this.id = id;
        this.productName = productName;
        this.categoryName = categoryName;
        this.stock = stock;
        this.price = price;
        this.isDeleted = isDeleted;
    }
}
