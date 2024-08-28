package com.sebas.shoppingcart.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class UpdateUser {
	@Min(1)
	private int id;
	@Size(min=2, message="Email should have at least 2 characters")
	private String email;
	@Size(min=2, message="Area of interest should have at least 2 characters")
	private String areaOfInterest;
	
	public UpdateUser(int id,String email,String areaOfInterest) {
		super();
		this.id = id;
		this.email = email;
		this.areaOfInterest = areaOfInterest;
	}
	
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
	public String getAreaOfInterest() {
		return areaOfInterest;
	}
	public void setAreaOfInterest(String areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}
}
