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
		
	@SuppressWarnings("unused")
	private Offer() {}
	
	public Offer(RuralHouse ruralHouse, Date startDate, Date endDate, double price) {
		this.ruralHouse = ruralHouse;
		this.startDate = startDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}
	
	public Offer(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate, double price) {
		this(ruralHouse, ParseDate.asDate(startDate), ParseDate.asDate(endDate), price);
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
		return String.format("Offer [id: %s, ruralHouse: %s, startDate: %s, endDate: %s, price: %s]", id, ruralHouse,
				startDate, endDate, price);
	}

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -7297495978806951469L;

}
