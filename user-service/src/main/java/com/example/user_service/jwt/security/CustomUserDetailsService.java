package com.example.user_service.jwt.security;


import com.example.user_service.domain.User;
import com.example.user_service.global.ErrorCode;
import com.example.user_service.global.exception.NotFoundUserException;
import com.example.user_service.jwt.dto.CustomUserDetails;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // 이메일로 사용자 조회
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException(ErrorCode.NOT_FOUND_USER));

        return new CustomUserDetails(user);
    }
}

