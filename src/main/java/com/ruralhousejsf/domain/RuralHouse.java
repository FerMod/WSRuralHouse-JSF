package com.ruralhousejsf.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code RuralHouse} is a {@code Serializable} class that represents a rural house.
 * 
 * @see Serializable
 */
public class RuralHouse implements Serializable {

	private long id;
	private String description;
	private String city; 
	private Set<Offer> offers;

	/**
	 * An empty constructor used by hibernate.
	 * <p>
	 * Hibernate creates objects via reflection using {@linkplain java.lang.Class#newInstance()} to 
	 * create a new instance the classes.For that reason, it requires a no-arg constructor of at least
	 * package visibility, to be able to instantiate this object.
	 */
	RuralHouse() {}

	/**
	 * Constructs a new RuralHouse with an empty offers set.
	 * 
	 * @param description the rural house description
	 * @param city the rural house city
	 */
	public RuralHouse(String description, String city) {
		this(description, city, new HashSet<Offer>());		
	}

	/**
	 * Constructs a new RuralHouse.
	 * 
	 * @param description the rural house description
	 * @param city the rural house city
	 * @param offers the offers set of this rural house
	 */
	public RuralHouse(String description, String city, Set<Offer> offers) {
		this.description = description;
		this.city = city;	
		this.offers = offers;
	}

	/**
	 * Returns the RuralHouse id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Change the RuralHouse id to a new one.
	 * 
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the value of this rural house description.
	 * 
	 * @return this rural houses description value
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Change this rural house description to the new one.
	 * 
	 * @param id the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the value of this rural house city.
	 * 
	 * @return this rural houses city value
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Change this rural house city to the new one.
	 * 
	 * @param id the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns a set of {@link Offers} that are available for this rural house.
	 * 
	 * @return this rural houses set of offers
	 */
	public Set<Offer> getOffers() {
		return offers;
	}

	/**
	 * Changes this rural house set of type {@link Offer} for the new passed one.
	 * 
	 * @param offers this rural houses new set of offers
	 */
	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	/**
	 * Adds the passed offer to the currently existing set of {@link Offer}.
	 * 
	 * @param offer the {@link Offer} to be added to the set, if not already present
	 * @return {@code true} if this set did not already contain the specified element
	 */
	public boolean addOffer(Offer offer) {
		return offers.add(offer);
	}

	/**
	 * Removes any {@link Offer} element from the offers set that match with the passed id.
	 * 
	 * @param id the {@link Offer} id to be removed from the set, if present
	 * @return {@code true} if any elements were removed
	 */
	public boolean removeOffer(long id) {
		return offers.removeIf(offer -> offer.getId().equals(id));
	}

	/**
	 * Removes the {@link Offer} element from the offers set that match with the passed id.
	 * This removes an element {@code e} such that <code>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</code>.
	 * 
	 * @param offer the {@link Offer} to be removed from this set, if present
	 * @return {@code true} if this set contained the specified element
	 */
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
