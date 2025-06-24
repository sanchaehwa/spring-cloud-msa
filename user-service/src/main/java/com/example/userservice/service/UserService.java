package com.example.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDto);

	Iterable<UserEntity> getUserByAll();

	UserDto getUserDetailByEmail(String userName);

}
