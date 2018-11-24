package com.ruralhousejsf.model.availability;

import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ruralhousejsf.AppFacade;

import businessLogic.ApplicationFacadeFactory;
import businessLogic.ApplicationFacadeInterface;
import domain.City;
import domain.Review.ReviewState;
import domain.RuralHouse;

@ManagedBean(name="queryAvailability")
@SessionScoped
public class QueryAvailabilityBean {

	private String ruralHouses;
	private String nights;
	private String date;

	private LinkedHashMap<String, Object> ruralHouseHashMap;
	private ApplicationFacadeInterface applicationFacade;
	
	public QueryAvailabilityBean() {
		
		applicationFacade = AppFacade.getInstance().getImpl();
		List<RuralHouse> ruralHouseList = applicationFacade.getRuralHouses(ReviewState.APPROVED);

		ruralHouseHashMap = new LinkedHashMap<String, Object>();
		for (RuralHouse ruralHouse : ruralHouseList) {
			ruralHouseHashMap.put(ruralHouse.getName(), ruralHouse);
		}

	}
	
	public LinkedHashMap<String, Object> getRuralHouseHashMap() {
		return ruralHouseHashMap;
	}
	
	public String[] getRuralHouseHashMapValues() {
		return (String[]) ruralHouseHashMap.keySet().toArray();
	}

	public String getNights() {
		return nights;
	}

	public void setNights(String firstName) {
		this.nights = firstName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String createRegistrationForm() {
		return "output";
	}
}
