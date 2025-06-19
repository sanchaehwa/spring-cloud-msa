package com.example.catalog_service.dto;

import com.example.catalog_service.domain.Catalog;
import com.example.catalog_service.domain.Product;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSaveRequest {

    @NotBlank(message = "상품 명을 작성해주세요 ")
    private String productName;

    @NotNull(message = "상품 가격을 입력해주세요")
    private Integer unitPrice;

    @NotNull(message = "상품 수량을 입력해주세요")
    private Integer stock;

    @NotBlank(message ="카테고리를 설정해주세요")
    private String categoryName;

    public Product toEntity(Catalog catalog) {
        return Product.builder()
                .productName(productName)
                .unitPrice(unitPrice)
                .stock(stock)
                .catalog(catalog)
                .build();
    }
}
