package com.ruralhousejsf.model.availability;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="setAvailability")
@SessionScoped
public class SetAvailabilityBean {
	
	public Date startDate;
	public Date endDate;
	public int priceOffer;
	
	public SetAvailabilityBean() {
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
	
	public int getPriceOffer() {
		return priceOffer;
	}

	public void setPriceOffer(int priceOffer) {
		this.priceOffer = priceOffer;
	}

	public String establecer() {
		return "lulz";
	}
	
}
