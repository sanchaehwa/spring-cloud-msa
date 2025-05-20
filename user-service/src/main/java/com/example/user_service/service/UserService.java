package com.example.user_service.service;

import com.example.user_service.domain.User;
import com.example.user_service.dto.UserSignUpRequest;
import com.example.user_service.global.ErrorCode;
import com.example.user_service.global.exception.DuplicateUserException;
import com.example.user_service.global.exception.NotFoundUserException;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    //회원가입
    @Transactional
    public Long saveUser(UserSignUpRequest userSignUpRequest) {
        //이메일로 이미 존재하는 회원인지 검증
        validateDuplicateUser(userSignUpRequest);

//        User user = modelMapper.map(userSignUpRequest, User.class); //UserSignUpRequest (DTO) -> User(엔티티로 변환할때)
//        user.encodePassword(passwordEncoder); //기본생성자 + Setter 사용
        //Setter 사용하지않고, 생성자 기반의 객체를 생상하는 @Builder 패턴을 사용하니깐 mapper 못씀
        User user = userSignUpRequest.toEntity();
        user.encodePassword(passwordEncoder);
        return userRepository.save(user).getId();

    }
    //회원 조회(상세조회)
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundUserException(ErrorCode.NOT_FOUND_USER));
    }
    //회원 전체 조회
    @Transactional(readOnly = true)
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    //이미 존재하는 회원인지 확인
    private void validateDuplicateUser(UserSignUpRequest userSignUpRequest) {
        if (userRepository.existsByEmail(userSignUpRequest.getEmail())) {
            throw new DuplicateUserException(ErrorCode.DUPLICATE_USER);
        }

    }


}
