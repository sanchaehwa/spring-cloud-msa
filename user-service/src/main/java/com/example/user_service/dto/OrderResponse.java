package com.example.user_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class OrderResponse {
    private Long productId; //상품 ID
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;
    private Long orderId; //주문 ID

}
