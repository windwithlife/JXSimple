package com.simple.base;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * An item in an order
 */
@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	private String name;
	private String address;
	private int age;
	private String email;
	

	

	

	/**
	 * @return the product
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the product
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the price
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the quantity
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}
