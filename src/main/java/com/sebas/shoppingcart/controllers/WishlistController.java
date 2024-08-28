package com.sebas.shoppingcart.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sebas.shoppingcart.model.Wishlist;
import com.sebas.shoppingcart.repos.WishlistRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class WishlistController {

	private WishlistRepository repository;
	
	@Autowired
	WishlistController(WishlistRepository repository){
		this.repository = repository;
	}

	
}
