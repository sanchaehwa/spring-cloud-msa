package com.example.catalogservice.controller;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.service.CatalogService;

import com.example.catalogservice.vo.ResponseCatalog;
import lombok.*;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.*;
@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return "It's working in User Service"
                + "\nport(local.server.port)=" + env.getProperty("local.server.port")
                + "\nport(server.port)=" + env.getProperty("server.port")
                + "\ntoken secret=" + env.getProperty("token.secret")
                + "\ntoken expiration time=" + env.getProperty("token.expiration_time");
    }
    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {

        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();
        List<ResponseCatalog> result = new ArrayList<>();
        catalogList.forEach( v -> {
            result.add(new ModelMapper().map(v,ResponseCatalog.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
