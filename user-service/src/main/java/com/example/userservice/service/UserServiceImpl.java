package com.example.userservice.service;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RefreshScope
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
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
	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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
	public UserDto getUserByUserId(String userId){
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		List<ResponseOrder> orders = new ArrayList<>();
		userDto.setOrders(orders);
		return userDto;
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
