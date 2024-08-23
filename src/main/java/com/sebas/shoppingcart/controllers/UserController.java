package com.sebas.shoppingcart.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value="/useremail",method=RequestMethod.GET)
	public ResponseEntity<?> getUserByName(@RequestParam(value="email") String email) {
		Optional<User> userWithEmail = repository.findByEmail(email);
		if(userWithEmail.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(userWithEmail);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found with that email");
	}
	
	@RequestMapping(value="/username",method=RequestMethod.GET)
	public ResponseEntity<?> getUserByInfo(@RequestParam(value="lastName",required=false) String lastName,
											@RequestParam(value="firstName",required=false) String firstName) {
		List<User> listFirst = repository.findByFirstName(firstName);
		List<User> listLast = repository.findByLastName(lastName);
		if(!listFirst.isEmpty() && !listLast.isEmpty()) {
			for(User u:listLast) {
				if(listFirst.contains(u)) {
					return ResponseEntity.status(HttpStatus.FOUND).body(u);
				}
			}
		}
		else if((!listFirst.isEmpty() && listLast.isEmpty()) || (listFirst.isEmpty() && !listLast.isEmpty())) {
			return listFirst.isEmpty() ? (ResponseEntity.status(HttpStatus.FOUND).body(listLast)) : ResponseEntity.status(HttpStatus.FOUND).body(listFirst);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found with that name");
	}
	
	
	@RequestMapping(value="/saveUser",method=RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		List<User> savedUsers = repository.findAll();
		for(User u:savedUsers) {
			if(user.getEmail().contains(u.getEmail())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		User oldUser = repository.findById(user.getId()).get();
		List<User> savedUsers = repository.findAll();
		for(User u:savedUsers) {
			if(user.getEmail().contains(u.getEmail())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
			}
		}
		oldUser.setEmail(user.getEmail());
		oldUser.setAreaOfInteres(user.getAreaOfInteres());
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(oldUser));
	}
	
	@RequestMapping(value="/deleteUser/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
		Optional<User> optUser = repository.findById(id);
		if(!optUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("User deleted");
	}
}
