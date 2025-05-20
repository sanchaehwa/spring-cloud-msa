package com.example.user_service.jwt.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
//JWT 를 생성하고 검증하며 필요한 정보를 추출
public class JWTUtil {
    private SecretKey secretKey;
    //비밀키 - 비밀키 객체 생성
    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    //토큰에서 이메일 추출
    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }

    //Role 추출
    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
    //Token 만료 여부
    public Boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration() //토큰 만료시간을 가져옴
                .before(new Date()); //만료시간이 현재시간보다 전이면 True (만료되었다는거)
    }
    //Refresh Token 인지 Access Token 인지
    public String getCategory(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("category", String.class);
    }
    //JWT 생성
    public String createJwt(String category, String email, String role, Long exp) {
        Date now = new Date();
        Date expDate = new Date(now.getTime() + exp);
        return Jwts.builder()
                .claim("category", category)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(now) //발급 시간은 현재 시간
                .expiration(expDate) //만료시간 + 만료기간
                .signWith(secretKey)
                .compact();
    }

}

