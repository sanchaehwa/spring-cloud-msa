package com.example.user_service.controller;

import com.example.user_service.Redis.RedisUtil;
import com.example.user_service.domain.User;
import com.example.user_service.dto.UserRequest;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;
    private final RedisUtil redisUtil;

    @GetMapping("/health_check")
    public String status(){
       return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
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
        return ResponseEntity.ok(ApiResponse.of(201, "회원가입이 완료되었습니다",userId));
    }
    //회원조회(상세조회)
    @Operation(summary = "회원 조회", description = "사용자의 ID로 회원을 조회합니다" )
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> findUser(@PathVariable Long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }


}
