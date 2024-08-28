package com.sebas.shoppingcart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Size(min=2, message="Name should have at least 2 characters")
	private String productName;
	@Column(name="total_products_inventory", nullable=false)
	@Size(min=1,message="No products in inventory specified")
	private int totalProductsInventory;
	@Size(min=1,message="No price of product specified")
	private double price;
	private String image;
	private String productDescription;
	@Size(min=1,message="No product status specified")
	private boolean productStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getTotalProductsInventory() {
		return totalProductsInventory;
	}
	public void setTotalProductsInventory(int totalProductsInventory) {
		this.totalProductsInventory = totalProductsInventory;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public boolean isProductStatus() {
		return productStatus;
	}
	public void setProductStatus(boolean productStatus) {
		this.productStatus = productStatus;
	}
}
