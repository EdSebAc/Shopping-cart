package com.sebas.shoppingcart.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebas.shoppingcart.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}
