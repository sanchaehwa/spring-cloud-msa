package com.example.catalog_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //하나의 상품 - 하나의 카테고리 카테고리 - 상품 여러개가 하나의 카테고리에 등록해 다대일(상품) 일대다(카테고리)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer unitPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date created_at;

    //삭제 여부
    @Column(columnDefinition = "TINYINT default 0")
    private boolean isDeleted;

    @Builder
    public Product(Catalog catalog, String productName, Integer stock, Integer unitPrice,Boolean isDeleted) {
        this.catalog = catalog;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.isDeleted = isDeleted;
    }

    public void addCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

}
