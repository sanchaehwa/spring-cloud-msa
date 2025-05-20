package com.example.user_service.controller;

import com.example.user_service.domain.User;
import com.example.user_service.global.response.ApiResponse;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class UserAdminController {

    private final UserService userService;
    //회원 전체 조회
    @Operation(summary = "회원 전체 조회", description = "어드민 사용자는 회원을 전체 조회할 수 있습니다")
    @GetMapping("/users")
    //페이징 처리 - 사용자가 옵션을 선택하면 그 옵션의 크기만큼 보여주고, 그렇지않으면 전체 조회처럼 동작하게 작성
    public ResponseEntity<ApiResponse<Page<User>>> findAllUsers(
        @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) { //MAX_VALUE가 전체조회처럼 동작하게해주는 부분
            Page<User> users = userService.findAllUsers(pageable);
            return ResponseEntity.ok(ApiResponse.ok(users));
        }

    }

