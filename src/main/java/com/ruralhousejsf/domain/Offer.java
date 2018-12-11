package com.ruralhousejsf.domain;


import java.io.Serializable;
import java.util.Date;


public class Offer implements Serializable {

<<<<<<< HEAD
	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -7297495978806951469L;
	
=======
>>>>>>> refs/remotes/origin/FerMod
	private long id;
	private RuralHouse ruralHouse;
	private Date startDate; 
	private Date endDate;
	private double price;
	
	public Offer() {
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
	
	@Override
	public String toString() {
		return "Offer [id=" + id + ", ruralHouse=" + ruralHouse + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", price=" + price + "]";
	}

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -7297495978806951469L;

}
