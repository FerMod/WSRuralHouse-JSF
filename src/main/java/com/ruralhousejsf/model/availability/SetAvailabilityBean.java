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

import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.businessLogic.ApplicationFacadeInterface;
import com.ruralhousejsf.exceptions.BadDatesException;


@ManagedBean(name="setAvailability")
@SessionScoped
public class SetAvailabilityBean {

	private Date startDate;
	private String ruralHouseLabel;
	private Date endDate;
	private double priceOffer;

	private LinkedHashMap<String, RuralHouse> ruralHouses;
	private ApplicationFacadeInterface applicationFacade;

	public SetAvailabilityBean() {

		applicationFacade = AppFacade.getImpl(false);
		List<RuralHouse> ruralHouseList = applicationFacade.getAllRuralHouses();

		ruralHouses = new LinkedHashMap<String, RuralHouse>();
		for (RuralHouse ruralHouse : ruralHouseList) {
			ruralHouses.put(ruralHouse.getId() + " : " + ruralHouse.getDescription(), ruralHouse);
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

	public ApplicationFacadeInterface getApplicationFacade() {
		return applicationFacade;
	}

	public void setApplicationFacade(ApplicationFacadeInterface applicationFacade) {
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
				getApplicationFacade().createOffer(rh, getStartDate(), getEndDate(), getPriceOffer());
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_INFO, "¡Oferta creada correctamente!", ""));
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
