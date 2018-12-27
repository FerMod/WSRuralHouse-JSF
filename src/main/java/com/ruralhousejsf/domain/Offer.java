package com.ruralhousejsf.domain;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import com.ruralhousejsf.domain.util.ParseDate;


public class Offer implements Serializable {

	private long id;
	private RuralHouse ruralHouse;
	private Date startDate; 
	private Date endDate;
	private double price;
		
	public Offer() {}
	
	public Offer(RuralHouse ruralHouse, Date startDate, Date endDate, double price) {
		this.ruralHouse = ruralHouse;
		this.startDate = startDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}
	
	public Offer(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate, double price) {
		this(ruralHouse, ParseDate.toDate(startDate), ParseDate.toDate(endDate), price);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RuralHouse getRuralHouse() {
		return ruralHouse;
	}

	public void setRuralHouse(RuralHouse ruralHouse) {
		this.ruralHouse = ruralHouse;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

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
