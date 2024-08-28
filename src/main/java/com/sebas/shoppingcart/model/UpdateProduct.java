package com.sebas.shoppingcart.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateProduct {
	@NotNull(message="ID should be specified")
	private int id;
	@NotNull(message="No price of product specified")
	@DecimalMin(value="0.01",message="Price should not be 0")
	private Double price;
	private String image;
	private String productDescription;
	@NotNull(message="Number of products should specified")
	@Min(value=0,message="There can't be less than 0 products")
	private int totalProductsInventory;
	
	public UpdateProduct(Double price, String image, String productDescription, int totalProductsInventory) {
		super();
		this.price = price;
		this.image = image;
		this.productDescription = productDescription;
		this.totalProductsInventory = totalProductsInventory;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
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
	public int getTotalProductsInventory() {
		return totalProductsInventory;
	}
	public void setTotalProductsInventory(int totalProductsInventory) {
		this.totalProductsInventory = totalProductsInventory;
	}
}
