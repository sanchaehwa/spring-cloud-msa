package com.example.user_service.controller;

import com.example.user_service.dto.UserSignUpRequest;
import com.example.user_service.global.response.ApiResponse;
import com.example.user_service.service.UserService;
import com.example.user_service.vo.Greeting;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status(){
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome(){
       // return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    //회원가입
    @Operation(summary = "회원가입", description = "사용자의 정보를 받아 회원가입을 진행합니다" )
    @PostMapping("users/signup")
    public ResponseEntity<ApiResponse<Long>> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest){
        Long userId = userService.saveUser(userSignUpRequest);
        log.info("회원가입 성공 userId ={}", userId);
        return ResponseEntity.ok(new ApiResponse<>(userId));
        //return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);

    }
}
