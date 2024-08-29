package com.sebas.shoppingcart.controllers;

import java.util.List;

public class NoMoreProductException extends RuntimeException{
	public NoMoreProductException() {};
	public NoMoreProductException(String message, List<String> boughtProducts) {
		super("There is no more inventory for the product: " + message + ". Products bought: "+boughtProducts);		
	}
}
