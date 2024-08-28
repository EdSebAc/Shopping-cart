package com.sebas.shoppingcart.controllers;

public class EmailAlreadyInUseException  extends RuntimeException{

	public EmailAlreadyInUseException(String message){
		super("Email: '" + message + "' already in use");
	}
}
