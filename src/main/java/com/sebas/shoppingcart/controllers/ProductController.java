package com.sebas.shoppingcart.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sebas.shoppingcart.model.Product;
import com.sebas.shoppingcart.model.UpdateProduct;
import com.sebas.shoppingcart.repos.ProductRepository;

import aj.org.objectweb.asm.Type;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class ProductController {
	private ProductRepository repository;
	
	@Autowired
	ProductController(ProductRepository repository){
		this.repository = repository;
	}
	
	@GetMapping(value="/products")
	public List<Product> getProducts(){
		return repository.findAll();
	}
	
	@GetMapping(value="/products/name")
	public List<Product> getProductByName(@RequestParam(value="productName") String productName) {
		List<Product> listName = repository.findByProductName(productName);
		if(listName.isEmpty()) {
			throw new ProductNotFoundException("name: " + productName);
		}
		return listName;
	}
	
	@GetMapping(value="/products/price")
	public ResponseEntity<?> getProductByName(@RequestParam(value="price") double price) {
		List<Product> listPrice = repository.findAll();
		for(Product p:listPrice) {
			if(p.getPrice() == price) {
				return ResponseEntity.status(HttpStatus.FOUND).body(p);
			}
		}
		throw new ProductNotFoundException("price: " + price);
	}
	
	@PostMapping(value="/products")
	public ResponseEntity<?> saveProduct(@Valid @RequestBody Product product){
		List<Product> existingProducts = repository.findAll();
		for(Product e:existingProducts) {
			if(e.getProductName().contains(product.getProductName())) {
				e.setTotalProductsInventory(e.getTotalProductsInventory() +1);
				return ResponseEntity.status(HttpStatus.FOUND).body(repository.save(e));
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
	}
	
	@PutMapping(value="/products")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateProduct product){
		Optional<Product> oldProduct = repository.findById(product.getId());
		if(oldProduct.isEmpty()) {
			throw new ProductNotFoundException("id: " + product.getId());
		}
		Product newProduct = oldProduct.get();
		newProduct.setPrice(product.getPrice());
		newProduct.setProductDescription(product.getProductDescription());
		newProduct.setImage(product.getImage());
		newProduct.setTotalProductsInventory(product.getTotalProductsInventory());
		Product savedProduct = repository.save(newProduct);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PatchMapping(value="/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id){
		Optional<Product> product = repository.findById(id);
		if(product.isEmpty()) {
			throw new ProductNotFoundException("id: " + id);
		}
		product.get().setProductStatus(false);
		repository.save(product.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted");			
	}
}
