package com.sebas.shoppingcart.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class WishlistNotFoundException extends RuntimeException {
	public WishlistNotFoundException(String message) {
		super("Wishlist with " + message + " not found");
	}
}
