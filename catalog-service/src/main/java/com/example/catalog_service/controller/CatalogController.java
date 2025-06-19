package com.example.catalog_service.controller;

import com.example.catalog_service.dto.CatalogDto;
import com.example.catalog_service.dto.CatalogSaveRequest;
import com.example.catalog_service.global.response.ApiResponse;
import com.example.catalog_service.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("catalog-service")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {
    Environment env;
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(Environment env,CatalogService catalogService) {
        this.env = env;
        this.catalogService = catalogService;
    }
    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    //카테고리 등록
    @Operation(summary="카테고리등록",description = "상품을 분류할 카테고리를 등록합니다")
    @PostMapping("/catalogs")
    public ResponseEntity<ApiResponse<Long>> createUser(@Valid @RequestBody CatalogSaveRequest catalogSaveRequest){
        Long catalogId = catalogService.saveCatalog(catalogSaveRequest);
        log.info("카테고리 등록 성공 catalogId ={}", catalogId);
        return ResponseEntity.ok(ApiResponse.of(201, "카테고리 등록이 완료되었습니다",catalogId));
    }
    //카테고리 전체 조회
    @Operation(summary="카테고리 전체 조회",description = "카테고리 전체 조회시, 이름만 조회됩니다")
    @GetMapping("/catalogs")
    public List<String> getAllCategoryNames(){
        return catalogService.getAllCatalogNames();
    }
    @Operation(summary="카테고리 상세 조회",description = "카테고리 상세 조회시, 해당 카테고리에 등록되어있는 상품명, 가격이 함께 조회됩니다.")
    @GetMapping("/catalogs/{id}")
    public List<CatalogDto> getCategoryDetail(@Valid @PathVariable Long id){
        return catalogService.getCatalogDetail(id);
    }

}
