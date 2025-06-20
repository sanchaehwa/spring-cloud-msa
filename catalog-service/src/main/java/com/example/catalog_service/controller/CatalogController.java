package com.example.catalog_service.controller;

import com.example.catalog_service.dto.CatalogDto;
import com.example.catalog_service.dto.CatalogSaveRequest;
import com.example.catalog_service.global.response.ApiResponse;
import com.example.catalog_service.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/health_check")
    public ResponseEntity<ApiResponse<String>> status() {
        String message = String.format("Catalog Service is running on port %s", env.getProperty("local.server.port"));
        return ResponseEntity.ok(ApiResponse.success("서비스 정상 작동 ", message));
    }

    @PostMapping("/catalogs")
    @Operation(summary = "카테고리 등록", description = "상품을 분류할 카테고리를 등록합니다")
    public ResponseEntity<ApiResponse<Long>> createCategory(@Valid @RequestBody CatalogSaveRequest request) {
        Long catalogId = catalogService.saveCatalog(request);
        log.info("카테고리 등록 완료: catalogId={}", catalogId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("카테고리 등록이 완료되었습니다.", catalogId));
    }

    @GetMapping("/catalogs")
    @Operation(summary = "카테고리 전체 조회", description = "카테고리 이름 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<String>>> getAllCategoryNames() {
        List<String> names = catalogService.getAllCatalogNames();
        return ResponseEntity.ok(ApiResponse.ok(names));
    }

    @GetMapping("/catalogs/{id}")
    @Operation(summary = "카테고리 상세 조회", description = "카테고리에 속한 상품명 및 가격을 조회합니다.")
    public ResponseEntity<ApiResponse<List<CatalogDto>>> getCategoryDetail(@PathVariable Long id) {
        List<CatalogDto> details = catalogService.getCatalogDetail(id);
        return ResponseEntity.ok(ApiResponse.ok(details));
    }
}
