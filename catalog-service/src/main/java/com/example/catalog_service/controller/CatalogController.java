package com.example.catalog_service.controller;

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
    @PostMapping("catalog/save")
    public ResponseEntity<ApiResponse<Long>> createUser(@Valid @RequestBody CatalogSaveRequest catalogSaveRequest){
        Long userId = catalogService.saveCatalog(catalogSaveRequest);
        log.info("카테고리 등록 성공 userId ={}", userId);
        return ResponseEntity.ok(ApiResponse.of(201, "카테고리 등록이 완료되었습니다",userId));
    }}
