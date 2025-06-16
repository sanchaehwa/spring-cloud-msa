package com.example.user_service.dto;

import com.example.user_service.domain.User;
import lombok.Builder;
import lombok.Getter;


import java.util.Date;
import java.util.List;

@Getter
@Builder
public class UserResponse {

    private Long userId;
    private String name;
    private String email;
    private List<OrderResponse> orders;
    private Date createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                //.orders(OrderResponse)
                .createdAt(user.getCreated_at())
                .build();
    }

}
