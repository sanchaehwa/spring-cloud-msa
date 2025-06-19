package com.example.catalog_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name="catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,length = 50)
    private String categoryName;

    @OneToMany(mappedBy = "catalog")
    private List<Product> products = new ArrayList<>();

    @Column(columnDefinition = "TINYINT default 0")
    private boolean isDeleted;

    public void addProduct(Product product) {
        products.add(product);
        product.addCatalog(this);
    }

    @Builder
    public Catalog(String categoryName, Boolean isDeleted) {
       this.categoryName = categoryName;
       this.isDeleted = isDeleted != null ? isDeleted : false;

    }

}
