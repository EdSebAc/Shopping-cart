package com.sebas.shoppingcart.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebas.shoppingcart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByProductName(String productName);
}
