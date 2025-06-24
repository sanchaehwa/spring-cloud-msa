package com.example.userservice.service;

import java.util.ArrayList;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.core.env.Environment;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final RestTemplate restTemplate;
	private final Environment env;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));

		return new User(
			userEntity.getEmail(),
			userEntity.getEncryptedPwd(),
			true,
			true,
			true,
			true,
			new ArrayList<>()
		);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

		userRepository.save(userEntity);

		return mapper.map(userEntity, UserDto.class);
	}


	@Override
	public Iterable<UserEntity> getUserByAll() {
		return userRepository.findAll();
	}

	@Override
	public UserDto getUserDetailByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException(email));

		return new ModelMapper().map(userEntity, UserDto.class);
	}
}
