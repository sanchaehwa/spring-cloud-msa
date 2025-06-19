package com.example.catalog_service.dto;
import com.example.catalog_service.domain.Catalog;
import com.example.catalog_service.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CatalogResponse {
    private Long catalogId;
    private String categoryName;
    private List<Product> products;
    private boolean isDeleted;

    public static CatalogResponse from(Catalog catalog) {
        return CatalogResponse.builder()
                .catalogId(catalog.getId())
                .categoryName(catalog.getCategoryName())
                .products(catalog.getProducts())
                .isDeleted(catalog.isDeleted())
                .build();
    }
}
