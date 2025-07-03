package com.example.userservice.security;

import static java.util.Objects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final UserService userService;
	private final Environment env;

	public AuthenticationFilter(AuthenticationManager authenticationManager,
		UserService userService, Environment env) {
		super.setAuthenticationManager(authenticationManager);
		this.userService = userService;
		this.env = env;
		//필터가 로그인 요청을 가로채도록 설정
		setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException {
		try {
			RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.getEmail(),
					creds.getPassword(),
					new ArrayList<>()
				)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		String userName = ((User)authResult.getPrincipal()).getUsername();
		UserDto userDetails = userService.getUserDetailByEmail(userName);

		String token = Jwts.builder()
			.setSubject(userDetails.getUserId())
			.setExpiration(new Date(System.currentTimeMillis() +
				Long.parseLong(requireNonNull(env.getProperty("token.expiration_time")))))
			.signWith(SignatureAlgorithm.HS256, requireNonNull(env.getProperty("token.secret")).getBytes())
			.compact();

		response.addHeader("token", token); //헤더로만 응답을 내러주고 있음
		response.addHeader("userId", userDetails.getUserId());

		//body에도 응답 표시
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String responseBody = String.format(
				"{\"token\":\"%s\", \"userId\":\"%s\"}",
				token, userDetails.getUserId()
		);
		response.getWriter().write(responseBody);

	}
}
