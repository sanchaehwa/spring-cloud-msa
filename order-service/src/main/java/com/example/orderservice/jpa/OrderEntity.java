package com.example.orderservice.jpa;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=120,unique=true)
    private String productId;

    @Column(nullable=false)
    private String qty; //수량

    @Column(nullable=false)
    private Integer unitPrice; //단가

    @Column(nullable=false)
    private Integer totalPrice;

    @Column(nullable = false,unique=true)
    private String userId;

    @Column(nullable = false,unique = true)
    private String orderId;

    @Column(nullable=false,updatable=false,insertable=false)
    @ColumnDefault(value="CURRENT_TIMESTAMP")
    private Date createdAt;
}
