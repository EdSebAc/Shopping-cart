package com.sebas.shoppingcart.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.sebas.shoppingcart.model.OrderHistory;
import com.sebas.shoppingcart.model.Product;
import com.sebas.shoppingcart.model.UpdateUser;
import com.sebas.shoppingcart.model.User;
import com.sebas.shoppingcart.model.Wishlist;
import com.sebas.shoppingcart.repos.OrderHistoryRepository;
import com.sebas.shoppingcart.repos.ProductRepository;
import com.sebas.shoppingcart.repos.UserRepository;
import com.sebas.shoppingcart.repos.WishlistRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class UserController {
	
	private UserRepository repository;
	private WishlistRepository wishlistRepository;
	private ProductRepository productRepository;
	private OrderHistoryRepository orderHistoryRepository;
	
	@Autowired
	UserController(UserRepository repository, WishlistRepository wishlistRepository,ProductRepository productRepository,OrderHistoryRepository orderHistoryRepository){
		this.repository = repository;
		this.wishlistRepository = wishlistRepository;
		this.productRepository = productRepository;
		this.orderHistoryRepository = orderHistoryRepository;
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
			throw new UserNotFoundException("email: "+email);
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
		throw new UserNotFoundException("first name: " + firstName + " & last name: " + lastName);
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
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("Id:"+id);
		}
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("User deleted");
	}
	
	@GetMapping("/users/{id}/wishlists")
	public List<Wishlist> retreiveWishlistForUser(@PathVariable int id){
		Optional<User> user = repository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("Id:"+id);
		}
		return user.get().getWishlist();
	}
	
	@PostMapping("/users/{id}/wishlists")
	public ResponseEntity<Object> createWishlistForUser(@PathVariable int id, @RequestBody Wishlist wishlist){
		Optional<User> user = repository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("Id:"+id);
		}
		wishlist.setUser(user.get());
		Wishlist savedWish = wishlistRepository.save(wishlist);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedWish.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("users/{id}/wishlists/{wid}")
	public ResponseEntity<Object> addProductToWishlist(@PathVariable int wid,@RequestParam int pid){
		Optional<Product> product = productRepository.findById(pid);
		if(product.isEmpty())
			throw new ProductNotFoundException("Id: " + pid);
		Optional<Wishlist> wishlist = wishlistRepository.findById(wid);
		if(wishlist.isEmpty())
			throw new WishlistNotFoundException("Id:"+wid);
		Wishlist foundWish = wishlist.get();
		foundWish.getProducts().add(product.get());
		Wishlist savedWish = wishlistRepository.save(foundWish);
		return ResponseEntity.status(HttpStatus.OK).body(savedWish);
	}
	
	@DeleteMapping("users/{id}/wishlists/{wid}")
	public ResponseEntity<Object> deleteProductFromWishlist(@PathVariable int wid,@RequestParam int pid){
		Optional<Product> product = productRepository.findById(pid);
		if(product.isEmpty())
			throw new ProductNotFoundException("Id: " + pid);
		Optional<Wishlist> wishlist = wishlistRepository.findById(wid);
		if(wishlist.isEmpty())
			throw new WishlistNotFoundException("Id:"+wid);
		Wishlist foundWish = wishlist.get();
		foundWish.setProducts(foundWish.getProducts().stream()
		.filter(p -> p.getId() != pid)
		.collect(Collectors.toList()));
		Wishlist savedWish = wishlistRepository.save(foundWish);
		return ResponseEntity.status(HttpStatus.OK).body(savedWish);
	}
	
	@DeleteMapping("users/{id}/wishlists")
	public ResponseEntity<Object> deleteEntireWishlist(@PathVariable int id, @RequestParam int wid){
		Optional<User> user = repository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("Id: "+id);
		Optional<Wishlist> wishlist = wishlistRepository.findById(wid);
		if(wishlist.isEmpty())
			throw new WishlistNotFoundException("Id:"+wid);
		wishlistRepository.deleteById(wid);
		return ResponseEntity.status(HttpStatus.OK).body("Wishlist deleted");
	}
	
	@PostMapping("users/{id}/buy")
	public ResponseEntity<Object> buyProducts(@PathVariable int id,@RequestBody List<Integer> products){
		Optional<User> user = repository.findById(id);
		OrderHistory orderHistory = new OrderHistory();
		orderHistory.setUser(user.get());
		if(user.isEmpty())
			throw new UserNotFoundException("Id: "+id);
		for(int p:products) {
			Optional<Product> foundProduct = productRepository.findById(p);
			if(foundProduct.isEmpty())
				throw new ProductNotFoundException("Id:"+p);
			if(foundProduct.get().getTotalProductsInventory()<1) {
				List<String> boughtProducts = new ArrayList<>();
				for(Product a:orderHistory.getProducts()) {
					boughtProducts.add(a.getProductName());
				}
				throw new NoMoreProductException(foundProduct.get().getProductName(), boughtProducts);
			}
			foundProduct.get().setTotalProductsInventory(foundProduct.get().getTotalProductsInventory()-1);
			productRepository.save(foundProduct.get());
			orderHistory.getProducts().add(foundProduct.get());
		}
		return ResponseEntity.status(HttpStatus.OK).body(orderHistoryRepository.save(orderHistory));
	}
	
	@GetMapping("/users/{id}/orders")
	public List<OrderHistory> retreiveOrdersForUser(@PathVariable int id){
		Optional<User> user = repository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("Id:"+id);
		}
		return user.get().getOrderHistory();
	}
}
