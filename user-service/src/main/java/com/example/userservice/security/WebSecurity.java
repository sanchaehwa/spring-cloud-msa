package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@RequiredArgsConstructor
public class WebSecurity {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment env;


	//Security Filter Chain 설정
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		return http
				.csrf(CsrfConfigurer::disable)
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/health_check")).permitAll()
						.requestMatchers(HttpMethod.POST, "/users").permitAll()
						.requestMatchers(new IpAddressMatcher("127.0.0.1")).permitAll()
						.anyRequest().authenticated()
				)
				.addFilter(new AuthenticationFilter(authenticationManager, userService, env))
				.build();
	}

	//AuthenticationManager 빈 등록 --> 2.7 버전에서권장되는 방식 인데 , Spring 내부에서 자동 구성된 AuthenticationManager 를 주입받는 방식
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// AuthenticationProvider (UserDetailsService + PasswordEncoder 설정)
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService); // UserService는 UserDetailsService를 구현해야 함
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		return provider;
	}
}
