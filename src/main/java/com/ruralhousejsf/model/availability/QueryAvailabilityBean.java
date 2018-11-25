package com.ruralhousejsf.model.availability;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.ruralhousejsf.AppFacade;

import domain.Offer;
import domain.Review.ReviewState;
import domain.RuralHouse;
import exceptions.BadDatesException;

@ManagedBean(name="queryAvailability")
@SessionScoped
public class QueryAvailabilityBean {

	private int nights;
	private Date startDate;
	private Date endDate;

	private String ruralHouseLabel;
	private LinkedHashMap<String, RuralHouse> ruralHouses;

	private List<Offer> offers;

	private AppFacade applicationFacade;

	public QueryAvailabilityBean() {

		applicationFacade = AppFacade.getInstance();
		List<RuralHouse> ruralHouseList = applicationFacade.getImpl().getRuralHouses(ReviewState.APPROVED);

		ruralHouses = new LinkedHashMap<String, RuralHouse>();
		for (RuralHouse ruralHouse : ruralHouseList) {
			ruralHouses.put(ruralHouse.getId() + " : " + ruralHouse.getName(), ruralHouse);
		}

	}

	public LinkedHashMap<String, RuralHouse> getRuralHouses() {
		return ruralHouses;
	}

	public String[] getRuralHousesValues() {
		Set<String> values = ruralHouses.keySet();
		return values.toArray(new String[values.size()]);
	}

	public void setRuralHouses(LinkedHashMap<String, RuralHouse> ruralHouses) {
		this.ruralHouses = ruralHouses;
	}

	public String getRuralHouseLabel() {
		return ruralHouseLabel;
	}

	public void setRuralHouseLabel(String ruralHouseLabel) {
		this.ruralHouseLabel = ruralHouseLabel;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
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

	public List<Offer> getOfferList() {
		return offers;
	}

	public void setOfferList(List<Offer> offerList) {
		this.offers = offerList;
	}

	public void dynamicRender(AjaxBehaviorEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		if(!context.isValidationFailed()) {
			
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(startDate); 
			calendar.add(Calendar.DATE, nights);
			endDate = calendar.getTime();
			
			RuralHouse ruralHouse = ruralHouses.get(ruralHouseLabel);
			try {
				offers =  applicationFacade.getImpl().getOffers(ruralHouse, getStartDate(), getEndDate());
			} catch (BadDatesException e) {
				e.printStackTrace();
				UIComponent target = event.getComponent().findComponent("messages");
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_INFO, "Bad Dates Exception", e.getMessage()));
				context.validationFailed();
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("Nights: " + nights + System.lineSeparator());
			sb.append("StartDate: " + startDate + System.lineSeparator());
			sb.append("EndDate: " + endDate + System.lineSeparator());
			sb.append("RuralHouseLabel: " + ruralHouseLabel + System.lineSeparator());
			sb.append("RuralHouses: " + ruralHouses + System.lineSeparator());
			sb.append("Offers: " + offers + System.lineSeparator());
			System.out.println(sb.toString());

			// TODO: Show offers

		}

	}

	private FacesMessage createMessage(FacesMessage.Severity severity, String summary, String content) {
		return new FacesMessage(severity, summary, content);
	}

}
