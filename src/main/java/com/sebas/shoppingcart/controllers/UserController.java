package com.sebas.shoppingcart.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sebas.shoppingcart.model.UpdateUser;
import com.sebas.shoppingcart.model.User;
import com.sebas.shoppingcart.repos.UserRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class UserController {
	
	private UserRepository repository;
	
	@Autowired
	UserController(UserRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return repository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable int id){
		Optional<User> user = repository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id);
		}
		return user.get();
	}
	
	@GetMapping("/users/email")
	public User getUserByName(@RequestParam(value="email") String email) {
		Optional<User> userWithEmail = repository.findByEmail(email);
		if(userWithEmail.isEmpty()) {
			throw new UserNotFoundException("Email: '"+email+"' not found");
		}
		return userWithEmail.get();
	}
	
	@GetMapping("/users/name")
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
	
	@PostMapping("/users")
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
		List<User> savedUsers = repository.findAll();
		for(User u:savedUsers) {
			if(user.getEmail().contains(u.getEmail())) {
				throw new EmailAlreadyInUseException(user.getEmail());
			}
		}
		User savedUser = repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/users")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUser user) {
		Optional<User> oldUser = repository.findById(user.getId());
		if(oldUser.isEmpty()) {
			throw new UserNotFoundException("Id: "+user.getId() + " not found");
		}
		User newUser = oldUser.get();
		List<User> savedUsers = repository.findAll();
		for(User u:savedUsers) {
			if(user.getEmail().contains(u.getEmail())) {
				throw new EmailAlreadyInUseException(user.getEmail());
			}
		}
		newUser.setEmail(user.getEmail());
		newUser.setAreaOfInterest(user.getAreaOfInterest());
		User savedUser = repository.save(newUser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
		Optional<User> optUser = repository.findById(id);
		if(!optUser.isEmpty()) {
			throw new UserNotFoundException("Id:"+id);
		}
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("User deleted");
	}
}
