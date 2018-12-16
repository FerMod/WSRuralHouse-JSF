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
	private Date endDate;
	private double priceOffer;

	private RuralHouse ruralHouse;
	private List<RuralHouse> ruralHouseList;
	
	private ApplicationFacadeInterface applicationFacade;

	public SetAvailabilityBean() {

		applicationFacade = AppFacade.getImpl();
		ruralHouseList = applicationFacade.getAllRuralHouses();

	}
	
	public List<RuralHouse> getRuralHouseList() {
		return ruralHouseList;
	}
	
	public RuralHouse getRuralHouse() {
		return ruralHouse;
	}

	public void setRuralHouse(RuralHouse ruralHouse) {
		this.ruralHouse = ruralHouse;
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

	public void setPriceOffer(double priceOffer) {
		this.priceOffer = priceOffer;
	}

	public ApplicationFacadeInterface getApplicationFacade() {
		return applicationFacade;
	}

	public void setApplicationFacade(ApplicationFacadeInterface applicationFacade) {
		this.applicationFacade = applicationFacade;
	}
	
	public String controlQueryAv() {
		return "queryav";
	}

	public void dynamicRender(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		if(!context.isValidationFailed()) {
			UIComponent target = event.getComponent().findComponent("setAvailability:msg");
			try {
				getApplicationFacade().createOffer(ruralHouse, startDate, endDate, priceOffer);
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_INFO, "¡Oferta creada correctamente!", "¡Oferta creada correctamente!"));
			} catch (BadDatesException e) {
				context.addMessage(target.getId(), createMessage(FacesMessage.SEVERITY_ERROR, "La oferta no puede tener fechas incompatibles.", "La oferta no puede tener fechas incompatibles."));
				context.validationFailed();
			}
		}
	}
	
	private FacesMessage createMessage(FacesMessage.Severity severity, String summary, String content) {
		return new FacesMessage(severity, summary, content);
	}

}
