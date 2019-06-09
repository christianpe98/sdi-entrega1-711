package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Offer {

	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String description;
	private Double price;
	private Boolean purchased = false;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "puchaser_id")
	private User purchaser;

	public Offer(String title, String description, Double price, User user) {
		super();
		this.description = description;
		this.price = price;
		this.user = user;
		this.title = title;
	}

	public Offer(Long id, String title, String description, Double price) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.title = title;
	}

	public Offer() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		if (price < 0) {
			throw new IllegalArgumentException("El precio de una oferta no puede ser negativo");
		}
		this.price = price;

	}

	public Boolean getPurchased() {
		return purchased;
	}

	public void setPurchased(Boolean purchased) {
		this.purchased = purchased;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public void setDate(Date date) {
		this.date = new Date(date.getTime());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(User purchaser) {
		this.purchaser = purchaser;
	}

	@Override
	public String toString() {
		return "Offer [title=" + title + ", description=" + description + ", price=" + price + ", purchased="
				+ purchased + ", date=" + date + ", user=" + user + ", purchaser=" + purchaser + "]";
	}

}
