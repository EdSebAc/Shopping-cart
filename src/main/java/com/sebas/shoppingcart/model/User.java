package com.sebas.shoppingcart.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Size(min=2, message="Email should have at least 2 characters")
	private String email;
	@Size(min=2, message="Last name should have at least 2 characters")
	private String lastName;
	@Size(min=2, message="First name should have at least 2 characters")
	private String firstName;
	@Column(name="area_of_interest")
	@Size(min=2, message="Area of interest should have at least 2 characters")
	private String areaOfInterest;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Wishlist> wishlist;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<OrderHistory> orderHistory;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAreaOfInterest() {
		return areaOfInterest;
	}
	public void setAreaOfInterest(String areaOfInteres) {
		this.areaOfInterest = areaOfInteres;
	}
	public List<Wishlist> getWishlist() {
		return wishlist;
	}
	public void setWishlist(List<Wishlist> wishlist) {
		this.wishlist = wishlist;
	}
	public List<OrderHistory> getOrderHistory() {
		return orderHistory;
	}
	public void setOrderHistory(List<OrderHistory> orderHistory) {
		this.orderHistory = orderHistory;
	}
}
