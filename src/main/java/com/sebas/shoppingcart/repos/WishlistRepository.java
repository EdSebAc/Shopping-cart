package com.sebas.shoppingcart.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebas.shoppingcart.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{
	
}
