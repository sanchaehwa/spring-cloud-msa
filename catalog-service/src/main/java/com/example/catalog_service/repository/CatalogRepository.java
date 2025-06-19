package com.example.catalog_service.repository;

import com.example.catalog_service.domain.Catalog;
import com.example.catalog_service.dto.CatalogDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    //삭제되지않는 카테고리중, 카테고리 이름 - 카테고리에 있는 상품 만 조회 : 카테고리는 하나인데 상품은 여러개 있을수 있으니깐 List
    @Query("select new com.example.catalog_service.dto.CatalogDto(" +
            "c.id, c.categoryName, p.productName, p.unitPrice, c.isDeleted) " +
            "from Catalog c left join c.products p " +
            "where c.isDeleted = false and c.id = :id")
    List<CatalogDto> findAllByIdAndIsDeletedFalse(Long id); // 복수 상품 → List//삭제되지않는것만 조회

    //삭제되지않는 카테고리만 전체 조회(이름만)
    @Query("select c.categoryName from Catalog c where c.isDeleted = false")
    List<String> findAllByIsDeletedFalse();

    //이름으로 조회
    boolean existsByCategoryNameAndIsDeletedFalse(String categoryName);

    Optional<Catalog> findByCategoryNameAndIsDeletedFalse(String categoryName);
}
