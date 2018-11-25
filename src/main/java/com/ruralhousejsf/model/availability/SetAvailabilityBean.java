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
	private String ruralHouse;
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

	public String getRuralHouse() {
		return ruralHouse;
	}

	public void setRuralHouse(String ruralHouse) {
		this.ruralHouse = ruralHouse;
	}
	
	public ApplicationFacadeInterface getApplicationFacade() {
		return applicationFacade;
	}

	public void setApplicationFacade(ApplicationFacadeInterface applicationFacade) {
		this.applicationFacade = applicationFacade;
	}
	
	public String establecer() {
		RuralHouse rh = (RuralHouse) getRuralHouseHashMap().get(ruralHouse);
		try {
			getApplicationFacade().createOffer(rh, getStartDate(), getEndDate(), getPriceOffer());
		} catch (OverlappingOfferException e) {
			throw new ValidatorException(new FacesMessage(
					"La oferta no debe tener fechas solapadas con otra oferta."));
		} catch (BadDatesException e) {
			throw new ValidatorException(new FacesMessage(
					"La oferta no debe tener fechas que no cumplan su rango correctamente."));
		}
		return "Oferta creada";
	}
	
}
