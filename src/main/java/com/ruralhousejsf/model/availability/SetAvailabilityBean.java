package com.ruralhousejsf.model.availability;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.validator.ValidatorException;

import com.ruralhousejsf.AppFacade;

import businessLogic.ApplicationFacadeInterface;
import domain.RuralHouse;
import exceptions.BadDatesException;
import exceptions.OverlappingOfferException;
import domain.Review.ReviewState;

@ManagedBean(name="setAvailability")
@SessionScoped
public class SetAvailabilityBean {
	
	private Date startDate;
	private String ruralHouses;
	private Date endDate;
	private double priceOffer;

	private LinkedHashMap<String, Object> ruralHouseHashMap;
	private ApplicationFacadeInterface applicationFacade;

	public SetAvailabilityBean() {
		
		applicationFacade = AppFacade.getInstance().getImpl();
		List<RuralHouse> ruralHouseList = applicationFacade.getRuralHouses(ReviewState.APPROVED);

		ruralHouseHashMap = new LinkedHashMap<String, Object>();
		for (RuralHouse ruralHouse : ruralHouseList) {
			ruralHouseHashMap.put(ruralHouse.getName(), ruralHouse);
		}
		
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
	
	public double getPriceOffer() {
		return priceOffer;
	}

	public void setPriceOffer(int priceOffer) {
		this.priceOffer = priceOffer;
	}

	public LinkedHashMap<String, Object> getRuralHouseHashMap() {
		return ruralHouseHashMap;
	}
	
	public String[] getRuralHouseHashMapValues() {
		return (String[]) ruralHouseHashMap.keySet().toArray();
	}
	
	public ApplicationFacadeInterface getApplicationFacade() {
		return applicationFacade;
	}

	public void setApplicationFacade(ApplicationFacadeInterface applicationFacade) {
		this.applicationFacade = applicationFacade;
	}
	
	public String establecer() {
		return "setok";
	}
	
}
