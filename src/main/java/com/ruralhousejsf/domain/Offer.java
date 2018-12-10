package com.ruralhousejsf.domain;


import java.io.Serializable;
import java.time.LocalDate;


public class Offer implements Serializable {

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -7297495978806951469L;
	
	private Long id;
	private RuralHouse ruralHouse;
	private LocalDate startDate; 
	private LocalDate endDate;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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

}
