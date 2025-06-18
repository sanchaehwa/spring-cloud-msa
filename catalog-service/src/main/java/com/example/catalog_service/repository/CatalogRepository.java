package com.example.catalog_service.repository;

import com.example.catalog_service.domain.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findByIdAndIsDeletedFalse(Long id); //삭제되지않는것만 조회
    //삭제되지않는 카테고리만 전체 조회
    List<Catalog> findAllByIsDeletedFalse();
    //이름으로 조회
    boolean existsByCategoryNameAndIsDeletedFalse(String categoryName);}
