package com.example.userservice.jpa;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUserId(String userId);

	Optional<UserEntity> findByEmail(String email);
}
