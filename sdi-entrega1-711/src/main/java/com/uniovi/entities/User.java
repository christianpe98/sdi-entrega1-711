package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role="ROLE_USER";
	
	private double balance;
	
	private static final double INIT_MONEY=100;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Offer> offers;
	
	@OneToMany(mappedBy = "purchaser", cascade = CascadeType.ALL)
	private Set<Offer> offersPurchased;

	private String password;
	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;

	public User(String email, String name, String lastName) {
		super();
		this.offers=new HashSet<Offer>();
		this.offersPurchased=new HashSet<Offer>();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.balance=INIT_MONEY;
	}

	public User() {
		this.balance=INIT_MONEY;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setOffers(Set<Offer> bids) {
		this.offers = bids;
	}

	public Set<Offer> getOffers() {
		return offers;
	}

	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public double getBalance() {
		return balance;
	}

	private void setBalance(double balance) {
		if(balance<0)
		{
			throw new IllegalArgumentException("El saldo no puede ser negativo");
		}
		this.balance = balance;
	}
	
	public void incrementBalance(double money)
	{
		setBalance(this.balance+money);
	}
	
	public void decrementBalance(double money)
	{
		setBalance(this.balance-money);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", lastName=" + lastName + ", role=" + role
				+ ", balance=" + balance + ", bids=" + offers + ", password=" + password + "]";
	}

	public Set<Offer> getOffersPurchased() {
		return offersPurchased;
	}

	public void setOffersPurchased(Set<Offer> offersPurchased) {
		this.offersPurchased = offersPurchased;
	}
	
	
}
