package com.ruralhousejsf.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RuralHouse implements Serializable {

	private long id;
	private String description;
	private String city; 
	private Set<Offer> offers;

	public RuralHouse() {}

	public RuralHouse(String description, String city) {
		this(description, city, new HashSet<Offer>());		
	}

	public RuralHouse(String description, String city, Set<Offer> offers) {
		this.description = description;
		this.city = city;	
		this.offers = offers;
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

	public boolean addOffer(Offer offer) {
		return offers.add(offer);
	}
	
	public boolean removeOffer(long id) {
		return offers.removeIf(offer -> offer.getId().equals(id));
	}
	
	public boolean removeOffer(Offer offer) {
		return offers.remove(offer);
	}

	@Override
	public String toString() {
		Set<Long> offerIdList = offers
				.stream()
				.map(o -> o.getId())
				.collect(Collectors.toSet());
		return String.format("RuralHouse [id: %s, description: %s, city: %s, offers: %s]", id, description, city, offerIdList);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}

		if (!RuralHouse.class.isAssignableFrom(obj.getClass())) {
			return false;
		}

		final RuralHouse other = (RuralHouse) obj;
		if (this.id != other.id) {
			return false;
		}

		if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
			return false;
		}

		if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
			return false;
		}
		
		if ((this.offers == null) ? (other.offers != null) : !this.offers.equals(other.offers)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + Long.hashCode(this.id);
		hash = 31 * hash + (this.description != null ? this.description.hashCode() : 0);
		hash = 31 * hash + (this.city != null ? this.city.hashCode() : 0);
		return hash;
	}


	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -7593429026088916515L;

}
