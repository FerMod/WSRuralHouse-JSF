package com.ruralhousejsf.model.availability;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="setAvailability")
@SessionScoped
public class AvailabilityBean {
	
	public Date startDate;
	public Date endDate;
	
	public AvailabilityBean() {
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
	
	public String establecer() {
		return "lulz";
	}
	
}
