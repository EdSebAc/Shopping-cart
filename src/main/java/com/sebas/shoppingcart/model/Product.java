package com.sebas.shoppingcart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String productName;
	@Column(name="total_products_inventory", nullable=false)
	private int totalProductsInventory;
	private double price;
	private String image;
	private String productDescription;
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
