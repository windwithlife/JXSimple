package com.simple.base.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.base.api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByIdAndPassword(Long id, String password);
	User findById(Long id);
	
}
