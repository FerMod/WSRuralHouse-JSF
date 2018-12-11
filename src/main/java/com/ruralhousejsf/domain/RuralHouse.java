package com.ruralhousejsf.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class RuralHouse implements Serializable {

	private long id;
	private String description;
	private String city; 
	private Set<Offer> offers;

	@SuppressWarnings("unused")
	private RuralHouse() {}

	public RuralHouse(String description, String city) {
		this.description = description;
		this.city = city;
	}

	public RuralHouse(String description, String city, Set<Offer> offers) {
		this(description, city);		
		this.offers = new HashSet<Offer>();
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	@Override
	public String toString() {
		return String.format("RuralHouse [id: %s, description: %s, city: %s, offers: %s]", id, description, city, offers);
	}

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -7593429026088916515L;


}
