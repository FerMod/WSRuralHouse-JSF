package com.ruralhousejsf.domain;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import com.ruralhousejsf.domain.util.ParseDate;

/**
 * The {@code Offer} is a {@code Serializable} class that represents an offer.
 * 
 * @see Serializable
 */
public class Offer implements Serializable {

	private long id;
	private RuralHouse ruralHouse;
	private Date startDate; 
	private Date endDate;
	private double price;

	/**
	 * An empty constructor used by hibernate.
	 * <p>
	 * Hibernate creates objects via reflection using {@linkplain java.lang.Class#newInstance()} to 
	 * create a new instance the classes.For that reason, it requires a no-arg constructor of at least
	 * package visibility, to be able to instantiate this object.
	 */
	Offer() {}

	/**
	 * Constructs a new Offer.
	 * 
	 * @param ruralHouse the rural house that this offer applies to
	 * @param startDate the offer start date
	 * @param endDate the offer end date
	 * @param price the offer price per day
	 */
	public Offer(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate, double price) {
		this(ruralHouse, ParseDate.toDate(startDate), ParseDate.toDate(endDate), price);
	}

	/**
	 * Constructs a new Offer.
	 *
	 * @param ruralHouse the rural house that this offer applies to
	 * @param startDate the offer start date
	 * @param endDate the offer end date
	 * @param price the offer price per day
	 */
	public Offer(RuralHouse ruralHouse, Date startDate, Date endDate, double price) {
		this.ruralHouse = ruralHouse;
		this.startDate = startDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}

	/**
	 * Returns the Offer id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Change the Offer id to a new one.
	 * 
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get this offers associated {@link RuralHouse}.
	 * 
	 * @return the rural house
	 */
	public RuralHouse getRuralHouse() {
		return ruralHouse;
	}

	/**
	 * Set this offers associated {@link RuralHouse} to the value passed as parameter .
	 * 
	 * @param ruralHouse the offers associated {@link RuralHouse}
	 */
	public void setRuralHouse(RuralHouse ruralHouse) {
		this.ruralHouse = ruralHouse;
	}

	/**
	 * Obtain this offers start date.
	 * 
	 * @return the offer start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Change this offers start date to the new date passed as parameter.
	 * 
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Obtain this offers end date.
	 * 
	 * @return the offer end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Change this offers end date to the new date passed as parameter.
	 * 
	 * @param endDate the end start date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Obtain this offers price cost.
	 * 
	 * @return the offers price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Change this offers price cost for the new price passed as parameter.
	 * 
	 * @param price the new price of this offer
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("Offer [id: %s, ruralHouse: %d, startDate: %s, endDate: %s, price: %s]", id, ruralHouse.getId(), startDate, endDate, price);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (!Offer.class.isAssignableFrom(obj.getClass())) {
			return false;
		}

		final Offer other = (Offer) obj;
		if (this.id != other.id) {
			return false;
		}

		if ((this.ruralHouse == null) ? (other.ruralHouse != null) : !this.ruralHouse.equals(other.ruralHouse)) {
			return false;
		}

		if ((this.startDate == null) ? (other.startDate != null) : !this.startDate.equals(other.startDate)) {
			return false;
		}

		if ((this.endDate == null) ? (other.endDate != null) : !this.endDate.equals(other.endDate)) {
			return false;
		}

		if (this.price != other.price) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + Long.hashCode(this.id);
		hash = 31 * hash + (this.ruralHouse != null ? this.ruralHouse.hashCode() : 0);
		hash = 31 * hash + (this.startDate != null ? this.endDate.hashCode() : 0);
		hash = 31 * hash + (this.endDate != null ? this.endDate.hashCode() : 0);
		hash = 31 * hash + Double.hashCode(this.price);
		return hash;
	}

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -7297495978806951469L;

}
