package com.example.user_service.dto;

import com.example.user_service.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpRequest {
    private String name;
    private String email;
    private String password;

    public User toEntity(){
        return User
                .builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

}
