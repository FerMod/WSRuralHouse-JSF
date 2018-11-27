package com.ruralhousejsf.model.availability;

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

	private LinkedHashMap<String, Object> ruralHouses;
	private ApplicationFacadeInterface applicationFacade;

	public SetAvailabilityBean() {

		applicationFacade = AppFacade.getInstance().getImpl();
		List<RuralHouse> ruralHouseList = applicationFacade.getRuralHouses(ReviewState.APPROVED);

		ruralHouses = new LinkedHashMap<String, Object>();
		for (RuralHouse ruralHouse : ruralHouseList) {
			ruralHouses.put(ruralHouse.getName(), ruralHouse);
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

	public LinkedHashMap<String, Object> getRuralHouses() {
		return ruralHouses;
	}

	public String[] getRuralHousesValues() {
		Set<String> values = ruralHouses.keySet();
		return values.toArray(new String[values.size()]);
	}

	public ApplicationFacadeInterface getApplicationFacade() {
		return applicationFacade;
	}

	public void setApplicationFacade(ApplicationFacadeInterface applicationFacade) {
		this.applicationFacade = applicationFacade;
	}

	public String getRuralHouse() {
		return ruralHouse;
	}

	public void setRuralHouse(String ruralHouse) {
		this.ruralHouse = ruralHouse;
	}

	public void dynamicRender(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		if(!context.isValidationFailed()) {
			RuralHouse rh = (RuralHouse) getRuralHouses().get(ruralHouse);
			UIComponent target = event.getComponent().findComponent("setAvailability:msg");
			try {
				getApplicationFacade().createOffer(rh, getStartDate(), getEndDate(), getPriceOffer());
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_INFO, "¡Oferta creada correctamente!", ""));
			} catch (OverlappingOfferException e) {
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_ERROR, "La oferta no puede tener fechas coincidentes a otra oferta.", e.getMessage()));
				context.validationFailed();
			} catch (BadDatesException e) {
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_ERROR, "La oferta no puede tener fechas incompatibles.", e.getMessage()));
				context.validationFailed();
			}
		}
	}
	
	private FacesMessage createMessage(FacesMessage.Severity severity, String summary, String content) {
		return new FacesMessage(severity, summary, content);
	}

}
