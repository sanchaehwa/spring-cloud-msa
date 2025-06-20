package com.example.catalog_service.controller;

import com.example.catalog_service.dto.ProductDto;
import com.example.catalog_service.dto.ProductSaveRequest;
import com.example.catalog_service.exception.ProductNotFoundException;
import com.example.catalog_service.global.ErrorCode;
import com.example.catalog_service.global.response.ApiResponse;
import com.example.catalog_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-service")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final Environment env;
    private final ProductService productService;

    @GetMapping("/health_check")
    public ResponseEntity<ApiResponse<String>> status() {
        String message = String.format("Product Service is running on port %s", env.getProperty("local.server.port"));
        return ResponseEntity.ok(ApiResponse.success("서비스 정상 작동", message));
    }

    @PostMapping("/products")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    public ResponseEntity<ApiResponse<Long>> createProduct(@Valid @RequestBody ProductSaveRequest request) {
        Long productId = productService.saveProduct(request);
        log.info("상품 등록 완료: productId={}", productId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("상품 등록이 완료되었습니다.", productId));
    }

    @GetMapping("/products")
    @Operation(summary = "상품 전체 조회", description = "전체 상품 목록 및 상세 정보를 반환합니다.")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProduct() {
        List<ProductDto> products = productService.findAllProducts();
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    @GetMapping("/products/{id}")
    @Operation(summary = "상품 단건 조회", description = "상품 ID로 상품 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable Long id) {
        ProductDto dto = productService.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.NOT_FOUND_PRODUCT_EXCEPTION));
        return ResponseEntity.ok(ApiResponse.ok(dto));
    }
}
