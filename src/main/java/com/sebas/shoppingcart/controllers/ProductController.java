package com.sebas.shoppingcart.controllers;

import java.io.Console;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.sebas.shoppingcart.model.Product;
import com.sebas.shoppingcart.repos.ProductRepository;

import aj.org.objectweb.asm.Type;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
	private ProductRepository repository;
	
	@Autowired
	ProductController(ProductRepository repository){
		this.repository = repository;
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public List<Product> getProducts(){
		return repository.findAll();
	}
	
	@RequestMapping(value="/name",method=RequestMethod.GET)
	public ResponseEntity<?> getProductByName(@RequestParam(value="productName") String productName) {
		List<Product> listName = repository.findByProductName(productName);
		if(!listName.isEmpty()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(listName);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found with that name");
	}
	
	@RequestMapping(value="/price",method=RequestMethod.GET)
	public ResponseEntity<?> getProductByName(@RequestParam(value="price") double price) {
		List<Product> listPrice = repository.findAll();
		for(Product p:listPrice) {
			if(p.getPrice() == price) {
				return ResponseEntity.status(HttpStatus.FOUND).body(p);
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found with that price");
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ResponseEntity<?> saveProduct(@RequestBody Product product){
		List<Product> existingProducts = repository.findAll();
		for(Product e:existingProducts) {
			if(e.getProductName().contains(product.getProductName())) {
				e.setTotalProductsInventory(e.getTotalProductsInventory() +1);
				return ResponseEntity.status(HttpStatus.FOUND).body(repository.save(e));
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
	}
	
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@RequestBody Product product){
		Optional<Product> oldProduct = repository.findById(product.getId());
		// price, image, description and total_products_inventory
		if(oldProduct.isPresent()) {
			Product newProduct = oldProduct.get();
			newProduct.setPrice(product.getPrice());
			newProduct.setProductDescription(product.getProductDescription());
			newProduct.setImage(product.getImage());
			newProduct.setTotalProductsInventory(product.getTotalProductsInventory());
			return ResponseEntity.status(HttpStatus.OK).body(repository.save(newProduct));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No product found");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.PUT)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id){
		Optional<Product> product = repository.findById(id);
		if(product.isPresent()) {
			product.get().setProductStatus(false);
			repository.save(product.get());
			return ResponseEntity.status(HttpStatus.OK).body("Product deleted");			
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No product found with that ID");
	}
}
