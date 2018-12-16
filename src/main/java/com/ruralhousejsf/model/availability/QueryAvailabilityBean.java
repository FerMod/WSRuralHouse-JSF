package com.ruralhousejsf.model.availability;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.businessLogic.ApplicationFacadeInterface;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.exceptions.BadDatesException;

@ManagedBean(name="queryAvailability")
@SessionScoped
public class QueryAvailabilityBean {

	private int nights;
	private Date startDate;
	private Date endDate;

	private RuralHouse ruralHouse;
	private List<RuralHouse> ruralHouseList;

	private List<Offer> offers;

	private ApplicationFacadeInterface applicationFacade;

	public QueryAvailabilityBean() {

		applicationFacade = AppFacade.getImpl();
		ruralHouseList = applicationFacade.getAllRuralHouses();

	}

	public List<RuralHouse> getRuralHouseList() {
		return ruralHouseList;
	}

	public void setRuralHouses(List<RuralHouse> ruralHouseList) {
		this.ruralHouseList = ruralHouseList;
	}

	public RuralHouse getRuralHouse() {
		return ruralHouse;
	}

	public void setRuralHouse(RuralHouse ruralHouse) {
		this.ruralHouse = ruralHouse;
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
	
	private Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date); 
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	public String controlSetAv() {
		return "setav";
	}

	public void dynamicRender(AjaxBehaviorEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		if(!context.isValidationFailed()) {
			
			endDate = addDays(startDate, nights);
			
			try {
				offers =  applicationFacade.getOffers(ruralHouse, getStartDate(), getEndDate());
			} catch (BadDatesException e) {
				e.printStackTrace();
				UIComponent target = event.getComponent().findComponent("queryAvailability:msg");
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_INFO, "Bad Dates Exception", e.getMessage()));
				context.validationFailed();
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("Nights: " + nights + System.lineSeparator());
			sb.append("StartDate: " + startDate + System.lineSeparator());
			sb.append("EndDate: " + endDate + System.lineSeparator());
			sb.append("RuralHouseLabel: " + ruralHouse + System.lineSeparator());
			sb.append("RuralHouses: " + ruralHouseList + System.lineSeparator());
			sb.append("Offers: " + offers + System.lineSeparator());
			System.out.println(sb.toString());

			// TODO: Show offers

		}

	}

	private FacesMessage createMessage(FacesMessage.Severity severity, String summary, String content) {
		return new FacesMessage(severity, summary, content);
	}

}
