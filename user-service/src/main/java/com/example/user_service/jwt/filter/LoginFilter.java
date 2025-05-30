package com.example.user_service.jwt.filter;

import com.example.user_service.Redis.RedisUtil;
import com.example.user_service.global.ErrorCode;
import com.example.user_service.jwt.config.JWTUtil;
import com.example.user_service.jwt.dto.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;
    public final ObjectMapper objectMapper = new ObjectMapper(); //JSON 파싱용

    //로그인 인증
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("로그인");
            Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), Map.class);
            String email = credentials.get("email");
            String password = credentials.get("password");

            //검증할수있도록 토큰에 담아 넘겨줌
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password,null); //권한 정보 -> 인증 이후에 Security Context 에 채워짐
            //권한 위임
            return authenticationManager.authenticate(authRequest);
        }
        catch(IOException e){
            throw new RuntimeException("Failed to parse login request JSON",e);
        }
    }
    //로그인 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException {
        log.info("Login success - 로그인 성공");
        //유저 정보
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().hasNext() ? authorities.iterator().next().getAuthority() : "ROLE_USER";

        //토큰 생성
        String access = jwtUtil.createJwt("access", email, role, 600000L); //10분 만료시간
        String refresh = jwtUtil.createJwt("refresh", email, role, 1800000L); //30분 만료시간

        //Access Token 저장
        response.setHeader("access_token", access);
        //refresh Token은 쿠키 - Redis에 저장
        redisUtil.saveRefreshToken(email, refresh);
        response.addCookie(createCookie("refresh", refresh));

        //응답 데이터 생성
        Map<String, Object> responseData = Map.of(
                "status", 200,
                "message", "로그인에 성공했습니다.",
                "data", Map.of(
                        "accessToken", access,
                        "user", Map.of(
                                "id", userDetails.getId(),
                                "email", userDetails.getUsername(),
                                "role", role
                        )
                )
        );
        //JSON 응답 반환
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseData));
    }


    //로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
    //쿠키 생성
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        //xss 공격으로부터 쿠키 보호
        cookie.setHttpOnly(true);
        //사이트 전체에서 쿠키 사용
        cookie.setPath("/");
        //쿠키 유효기간 *2주
        cookie.setMaxAge(14*24*60*60);
        return cookie;
    }

}
