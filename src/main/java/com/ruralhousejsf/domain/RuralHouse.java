package com.ruralhousejsf.domain;

import java.io.Serializable;
import java.util.Set;


public class RuralHouse implements Serializable {

<<<<<<< HEAD
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -7593429026088916515L;
	
	private long id;
=======
	private Long id;
>>>>>>> refs/remotes/origin/FerMod
	private String description;
	private String city; 
	private Set<Offer> offers;
	
	public RuralHouse() {
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
		return "RuralHouse [id=" + id + ", description=" + description + ", city=" + city + "]";
	}

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -7593429026088916515L;
	

}
