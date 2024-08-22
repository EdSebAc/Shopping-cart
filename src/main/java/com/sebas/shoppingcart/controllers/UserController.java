package com.sebas.shoppingcart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sebas.shoppingcart.model.User;
import com.sebas.shoppingcart.repos.UserRepository;

@RestController
@RequestMapping("/")
@CrossOrigin
public class UserController {
	
	private UserRepository repository;
	
	@Autowired
	UserController(UserRepository repository){
		this.repository = repository;
	}
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public List<User> getUsers(){
		return repository.findAll();
	}
	
	public User saveUser(@RequestBody User user) {
		return repository.save(user);
	}
}
