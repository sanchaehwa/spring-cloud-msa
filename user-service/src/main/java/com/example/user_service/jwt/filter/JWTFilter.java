package com.example.user_service.jwt.filter;


import com.example.user_service.domain.User;
import com.example.user_service.global.ErrorCode;
import com.example.user_service.jwt.config.JWTUtil;
import com.example.user_service.jwt.dto.CustomUserDetails;
import com.example.user_service.jwt.exception.InvalidTokenException;
import com.example.user_service.jwt.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request Authorization 헤더에 담음
        String AccessToken = request.getHeader(AUTH_HEADER);

        if (AccessToken == null || !AccessToken.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("authorization now");
        //순수토큰 획득(Barer 추출하고 순수 토큰만 사용)
        String accessToken = AccessToken.substring(BEARER.length());

        //토큰 만료시, 다음 필터로 넘기지 않음.
        if (jwtUtil.isTokenExpired(accessToken)) {
            log.debug("Access token is expired");
            throw new InvalidTokenException(ErrorCode.TOKEN_EXPIRED);
        }
        try {
            //토큰 유효시의 동작
            String email = jwtUtil.getEmail(accessToken);

            User user = User.builder().email(email).build();

            //이메일 기반으로 사용자 로딩
            CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);
            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            //인증 객체를 만들어서
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            log.error("JWT token parsing failed", e);
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        }
        filterChain.doFilter(request, response);

    }
}
