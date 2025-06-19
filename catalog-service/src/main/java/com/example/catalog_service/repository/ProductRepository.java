package com.example.catalog_service.repository;

import com.example.catalog_service.domain.Product;
import com.example.catalog_service.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //상품 하나 조회 * catalog, productName, stock, unitPrice 만 정보 얻어
    @Query("select new com.example.catalog_service.dto.ProductDto(" +
            "p.id, c.categoryName, p.productName, p.stock, p.unitPrice, p.isDeleted) " +
            "from Product p join p.catalog c " +
            "where p.isDeleted = false and p.id = :id")
    Optional<ProductDto> findProductDtoById(Long id);

    //상품 여러개 조회
    @Query("select new com.example.catalog_service.dto.ProductDto(" +
            "p.id, c.categoryName, p.productName, p.stock, p.unitPrice, p.isDeleted) " +
            "from Product p join p.catalog c " +
            "where p.isDeleted = false")
    List<ProductDto> findAllProductDto();

    boolean existsByProductNameAndIsDeletedFalse(String productName);

}
