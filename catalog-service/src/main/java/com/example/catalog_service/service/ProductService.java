package com.example.catalog_service.service;

import com.example.catalog_service.domain.Catalog;
import com.example.catalog_service.domain.Product;
import com.example.catalog_service.dto.ProductDto;
import com.example.catalog_service.dto.ProductSaveRequest;
import com.example.catalog_service.exception.DuplicateProductException;
import com.example.catalog_service.exception.ExistCategoryException;
import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.repository.CatalogRepository;
import com.example.catalog_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;

    @Transactional
    public Long saveProduct(ProductSaveRequest productSaveRequest) {
        // 중복 상품명 검사
        validateProduct(productSaveRequest.getProductName());

        // 존재하는 카테고리 조회 (없으면 예외 발생)
        Catalog catalog = catalogRepository.findByCategoryNameAndIsDeletedFalse(productSaveRequest.getCategoryName())
                .orElseThrow(() -> new ExistCategoryException(ErrorCode.EXIST_CATALOG_EXCEPTION));

        // Product 엔티티로 변환 및 저장
        Product product = productSaveRequest.toEntity(catalog);

        return productRepository.save(product).getId();
    }

    // 중복 상품명 검사
    private void validateProduct(String productName) {
        if (productRepository.existsByProductNameAndIsDeletedFalse(productName)) {
            throw new DuplicateProductException(ErrorCode.DUPLICATE_PRODUCT_EXCEPTION);
        }
    }
    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts() {
        return productRepository.findAllProductDto();
    }
    @Transactional(readOnly = true)
    public Optional<ProductDto> findProductById(Long id) {
        return productRepository.findProductDtoById(id);
    }

}
