package com.sebas.shoppingcart.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderHistory {
	public OrderHistory() {}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int orderId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User user;
	@ManyToMany
	private List<Product> products = new ArrayList<>();
	
	public OrderHistory(int orderId, User user, List<Product> products) {
		this.orderId=orderId;
		this.user=user;
		this.products=products;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int id) {
		this.orderId = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
