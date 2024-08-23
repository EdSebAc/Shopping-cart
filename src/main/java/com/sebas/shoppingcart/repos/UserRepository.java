package com.sebas.shoppingcart.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebas.shoppingcart.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	List<User> findByFirstName(String firstName);
	List<User> findByLastName(String lastName);
}
