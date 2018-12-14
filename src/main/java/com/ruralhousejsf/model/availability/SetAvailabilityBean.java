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

import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.exceptions.BadDatesException;
import com.ruralhousejsf.exceptions.OverlappingOfferException;

@ManagedBean(name="setAvailability")
@SessionScoped
public class SetAvailabilityBean {

	private Date startDate;
	private String ruralHouseLabel;
	private Date endDate;
	private double priceOffer;

	private LinkedHashMap<String, RuralHouse> ruralHouses;
	private AppFacade applicationFacade;

	public SetAvailabilityBean() {

		applicationFacade = AppFacade.getInstance();
		List<RuralHouse> ruralHouseList = applicationFacade.getImpl().getRuralHouses(ReviewState.APPROVED);

		ruralHouses = new LinkedHashMap<String, RuralHouse>();
		for (RuralHouse ruralHouse : ruralHouseList) {
			ruralHouses.put(ruralHouse.getId() + " : " + ruralHouse.getName(), ruralHouse);
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

	public LinkedHashMap<String, RuralHouse> getRuralHouses() {
		return ruralHouses;
	}

	public String[] getRuralHousesValues() {
		Set<String> values = ruralHouses.keySet();
		return values.toArray(new String[values.size()]);
	}

	public AppFacade getApplicationFacade() {
		return applicationFacade;
	}

	public void setApplicationFacade(AppFacade applicationFacade) {
		this.applicationFacade = applicationFacade;
	}

	public String getRuralHouseLabel() {
		return ruralHouseLabel;
	}

	public void setRuralHouse(String ruralHouseLabel) {
		this.ruralHouseLabel = ruralHouseLabel;
	}

	public void dynamicRender(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		if(!context.isValidationFailed()) {
			RuralHouse rh = getRuralHouses().get(getRuralHouseLabel());
			UIComponent target = event.getComponent().findComponent("setAvailability:msg");
			try {
				getApplicationFacade().getImpl().createOffer(rh, getStartDate(), getEndDate(), getPriceOffer());
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
